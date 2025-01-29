package br.com.mouts.order.application.usecase;

import br.com.mouts.order.application.OrderResponseDTO;
import br.com.mouts.order.domain.model.Order;
import br.com.mouts.order.domain.repository.OrderRepository;
import br.com.mouts.order.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindOrderByIdUseCaseTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private FindOrderByIdUseCase findOrderByIdUseCase;

	@Test
	void shouldThrowExceptionWhenOrderIdIsNull() {
		assertThrows(IllegalArgumentException.class, () -> findOrderByIdUseCase.execute(null));
		verify(orderRepository, never()).findById(anyString());
	}

	@Test
	void shouldReturnOrderWhenOrderIdIsNotNull() {
		// given
		UUID orderId = UUID.randomUUID();
		List<Product> products = List.of(new Product(UUID.randomUUID().toString(), "Product 1", "Product 1 description", 10, BigDecimal.TEN));
		Order order = new Order(orderId, UUID.randomUUID().toString(), products);
		when(orderRepository.findById(anyString())).thenReturn(Optional.of(order));

		// when
		String orderIdString = orderId.toString();
		OrderResponseDTO orderResponseDTO = assertDoesNotThrow(() -> findOrderByIdUseCase.execute(orderIdString));

		// then
		verify(orderRepository).findById(orderIdString);
		assert orderResponseDTO != null;
		assert orderResponseDTO.customerId().equals(order.getCustomerId());
		assert orderResponseDTO.products().size() == 1;

	}

}