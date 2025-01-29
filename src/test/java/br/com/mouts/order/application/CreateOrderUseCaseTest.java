package br.com.mouts.order.application;

import br.com.mouts.order.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {


	@Spy
	private OrderRepository orderRepository;

	@Spy
	private ApplicationEventPublisher eventPublisher;

	@InjectMocks
	private CreateOrderUseCase createOrderUseCase;

	@Captor
	private ArgumentCaptor<Order> orderArgumentCaptor;

	@Test
	void shouldCreateOrder() {
		// given
		String customerId = UUID.randomUUID().toString();
		UUID orderId = UUID.randomUUID();
		Product product = new Product(UUID.randomUUID().toString(), "Name", "Description", 10, BigDecimal.TEN);

		// when
		createOrderUseCase.execute(orderId, customerId, List.of(product));

		// then
		verify(orderRepository, timeout(2000)).save(orderArgumentCaptor.capture());
		Order orderCreated = orderArgumentCaptor.getValue();
		assert orderCreated.getId() != null;
		assert orderCreated.getCustomerId().equals(customerId);
		assert orderCreated.getProducts().size() == 1;
		assert orderCreated.getStatus().equals(OrderStatus.CREATED);
		assert orderCreated.getTotal().equals(BigDecimal.ZERO);
		assert !orderCreated.getId().isBlank();
	}
}