package br.com.mouts.order.application;

import br.com.mouts.order.domain.OrderCreateRequestEvent;
import br.com.mouts.order.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendOrderRequestEventUseCaseTest {


	@Mock
	private ApplicationEventPublisher eventPublisher;

	@InjectMocks
	private SendOrderRequestEventUseCase sendOrderRequestEventUseCase;

	@Captor
	private ArgumentCaptor<OrderCreateRequestEvent> eventCaptor;

	@Test
	void shouldSendRequestEventOrder() {
		// given
		ProductDTO product = new ProductDTO(UUID.randomUUID().toString(), "Name", "Description", 10, BigDecimal.TEN);
		OrderDTO input = new OrderDTO(UUID.randomUUID().toString(), List.of(product));

		// when
		sendOrderRequestEventUseCase.execute(input);

		// then
		verify(eventPublisher).publishEvent(eventCaptor.capture());
		OrderCreateRequestEvent event = eventCaptor.getValue();
		assert event.customerId().equals(input.customerId());
		assert event.products().size() == 1;
		Product eventProduct = event.products().getFirst();
		assert eventProduct.getId().equals(product.id());
		assert eventProduct.getName().equals(product.name());
		assert eventProduct.getDescription().equals(product.description());
		assert eventProduct.getQuantity().equals(product.quantity());
		assert eventProduct.getPrice().equals(product.price());
	}
}