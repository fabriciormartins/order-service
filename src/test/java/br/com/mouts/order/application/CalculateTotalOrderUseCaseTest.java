package br.com.mouts.order.application;

import br.com.mouts.order.domain.Order;
import br.com.mouts.order.domain.OrderRepository;
import br.com.mouts.order.domain.OrderStatus;
import br.com.mouts.order.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CalculateTotalOrderUseCaseTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private CalculateTotalOrderUseCase calculateTotalOrderUseCase;

	@Captor
	private ArgumentCaptor<Order> orderCaptor;


	@Test
	void shouldCalculateTotalOrder() {

		// given
		String customerId = UUID.randomUUID().toString();
		List<Product> products = List.of(new Product(UUID.randomUUID().toString(), "Product 1", "Product 1 Description",  2, BigDecimal.TEN));
		Order order = new Order(UUID.randomUUID(), customerId, products);
		String orderId = order.getId();
		Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

		// when
		calculateTotalOrderUseCase.execute(orderId);

		//then
		Mockito.verify(orderRepository).findById(orderId);
		Mockito.verify(orderRepository).save(orderCaptor.capture());
		Order capturedOrder = orderCaptor.getValue();
		assert capturedOrder.getTotal().compareTo(BigDecimal.valueOf(20)) == 0;
		assert capturedOrder.getProducts().size() == 1;
		assert capturedOrder.getProducts().getFirst().getQuantity() == 2;
		assert capturedOrder.getProducts().getFirst().getPrice().compareTo(BigDecimal.TEN) == 0;
		assert capturedOrder.getProducts().getFirst().getName().equals("Product 1");
		assert capturedOrder.getProducts().getFirst().getDescription().equals("Product 1 Description");
		assert capturedOrder.getProducts().getFirst().getId() != null;
		assert capturedOrder.getProducts().getFirst().getId().equals(products.getFirst().getId());
		assert capturedOrder.getStatus().equals(OrderStatus.WAITING_PAYMENT);

	}

	@Test
	void shouldThrowExceptionWhenOrderNotFound() {
		// given
		String orderId = UUID.randomUUID().toString();

		// when
		Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		// then
		try {
			calculateTotalOrderUseCase.execute(orderId);
		} catch (Exception e) {
			assert e.getClass().equals(IllegalArgumentException.class);
			assert e.getMessage().equals("Order not found: %s".formatted(orderId));
		}
	}

	@Test
	void shouldThrowExceptionWhenOrderIdIsBlank() {
		// given
		String orderId = "";

		// when
		try {
			calculateTotalOrderUseCase.execute(orderId);
		} catch (Exception e) {
			assert e.getMessage().equals("Order id cannot be empty");
		}
	}



}