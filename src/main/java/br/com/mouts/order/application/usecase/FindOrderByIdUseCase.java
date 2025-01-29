package br.com.mouts.order.application.usecase;

import br.com.mouts.order.application.OrderResponseDTO;
import br.com.mouts.order.application.ProductDTO;
import br.com.mouts.order.domain.model.Order;
import br.com.mouts.order.domain.repository.OrderRepository;

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
