package br.com.mouts.order.application;

import br.com.mouts.order.application.usecase.CalculateTotalOrderUseCase;
import br.com.mouts.order.application.usecase.CreateOrderUseCase;
import br.com.mouts.order.domain.event.OrderCreateRequestEvent;
import br.com.mouts.order.domain.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
