package br.com.mouts.order.application;

import br.com.mouts.order.domain.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderEventListener {

	private final CalculateTotalOrderUseCase calculateTotalOrderUseCase;

	private final CreateOrderUseCase createOrderUseCase;

	public OrderEventListener(CalculateTotalOrderUseCase calculateTotalOrderUseCase, CreateOrderUseCase createOrderUseCase) {
		this.calculateTotalOrderUseCase = calculateTotalOrderUseCase;
		this.createOrderUseCase = createOrderUseCase;
	}

	@EventListener(classes = OrderCreatedEvent.class)
	public void handle(OrderCreatedEvent event) {
		this.calculateTotalOrderUseCase.execute(event.orderId());
	}

	@EventListener(classes = OrderCreateRequestEvent.class)
	public void handle(OrderCreateRequestEvent event) {
		this.createOrderUseCase.execute(event.orderId(), event.customerId(), event.products());
	}
}
