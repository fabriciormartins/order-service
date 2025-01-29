package br.com.mouts.order.application;

import br.com.mouts.order.UseCase;
import br.com.mouts.order.domain.Order;
import br.com.mouts.order.domain.OrderRepository;

import java.util.Objects;
import java.util.Optional;

@UseCase
public class CalculateTotalOrderUseCase {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CalculateTotalOrderUseCase.class);

	private final OrderRepository orderRepository;

	public CalculateTotalOrderUseCase(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void execute(String orderId) {
		Objects.requireNonNull(orderId, "Order id cannot be null");
		if (orderId.isBlank()) {
			log.info("Order id cannot be empty");
			throw new IllegalArgumentException("Order id cannot be empty");
		}
		Optional<Order> orderOptional = this.orderRepository.findById(orderId);

		Order order = orderOptional.orElseThrow(() -> {
			log.warn("Order not found: {}", orderId);
			return new IllegalArgumentException("Order not found: %s".formatted(orderId));});
		order.calculateTotal();
		this.orderRepository.save(order);
	}
}
