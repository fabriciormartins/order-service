package br.com.mouts.order.application.usecase;

import br.com.mouts.order.domain.model.Order;
import br.com.mouts.order.domain.repository.OrderRepository;
import br.com.mouts.order.domain.model.Product;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@UseCase
public class CreateOrderUseCase {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CreateOrderUseCase.class);

	private final OrderRepository orderRepository;

	public CreateOrderUseCase(OrderRepository orderRepository){
		this.orderRepository = orderRepository;
	}

	public void execute(UUID orderId, String customerId, List<Product> products) {
		Order order = new Order(orderId, customerId, products);
		CompletableFuture.runAsync(() -> this.orderRepository.save(order))
				.whenCompleteAsync((v, throwable) -> {
					if (throwable != null) {
						log.error("Error saving order: {}", throwable.getMessage());
						return;
					}
					log.info("Order created: {}", order.getId());
				});
	}
}
