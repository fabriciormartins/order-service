package br.com.mouts.order.application;

import br.com.mouts.order.UseCase;
import br.com.mouts.order.domain.Order;
import br.com.mouts.order.domain.OrderRepository;

import java.util.List;

@UseCase
public class FindOrderByIdUseCase {

	private final OrderRepository orderRepository;

	public FindOrderByIdUseCase(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public OrderResponseDTO execute(String orderId) {
		if (orderId == null || orderId.isBlank()) {
			throw new IllegalArgumentException("Order id cannot be null or blank");
		}
		Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
		List<ProductDTO> productDTOS = order.getProducts().stream()
				.map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice())).toList();
		return new OrderResponseDTO(order.getCustomerId(), productDTOS, order.getTotal(), order.getStatus());
	}
}
