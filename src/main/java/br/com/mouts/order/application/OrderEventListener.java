package br.com.mouts.order.application;

import br.com.mouts.order.domain.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderEventListener {

	private final OrderRepository orderRepository;

	public OrderEventListener(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@EventListener(classes = OrderCreatedEvent.class)
	public void handle(OrderCreatedEvent event) {
		this.orderRepository.findById(event.orderId()).ifPresent(order -> {
			order.calculateAmmount();
			this.orderRepository.save(order);
		});
	}

	@EventListener(classes = OrderCreateRequestEvent.class)
	public void handle(OrderCreateRequestEvent event) {
		Order order = new Order(event.customerId(), event.products());
		CompletableFuture.runAsync(() -> this.orderRepository.save(order))
				.whenCompleteAsync((v, throwable) -> {
					if (throwable != null) {
						System.out.println("Error saving order: " + throwable.getMessage());
						return;
					}
					System.out.println("Order created: " + order.getId());
				});
	}
}
