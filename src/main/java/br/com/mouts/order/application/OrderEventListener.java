package br.com.mouts.order.application;

import br.com.mouts.order.application.usecase.CalculateTotalOrderUseCase;
import br.com.mouts.order.application.usecase.CreateOrderUseCase;
import br.com.mouts.order.application.usecase.SendOrderCheckoutUseCase;
import br.com.mouts.order.domain.event.OrderTotalCalculatedEvent;
import br.com.mouts.order.domain.event.OrderCreateRequestEvent;
import br.com.mouts.order.domain.event.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderEventListener {

	private static final org.slf4j.Logger  log = org.slf4j.LoggerFactory.getLogger(OrderEventListener.class);
	private final CalculateTotalOrderUseCase calculateTotalOrderUseCase;

	private final CreateOrderUseCase createOrderUseCase;

	private final SendOrderCheckoutUseCase sendOrderCheckoutUseCase;

	public OrderEventListener(CalculateTotalOrderUseCase calculateTotalOrderUseCase, CreateOrderUseCase createOrderUseCase, SendOrderCheckoutUseCase sendOrderCheckoutUseCase) {
		this.calculateTotalOrderUseCase = calculateTotalOrderUseCase;
		this.createOrderUseCase = createOrderUseCase;
		this.sendOrderCheckoutUseCase = sendOrderCheckoutUseCase;
	}

	@EventListener(classes = OrderCreatedEvent.class)
	public void handle(OrderCreatedEvent event) {
		log.info("Receive Order created event: {}", event);
		CompletableFuture.runAsync( () -> this.calculateTotalOrderUseCase.execute(event.orderId()))
				.whenCompleteAsync((unused, throwable) -> {
					if (throwable != null) {
						log.error("Error calculating total order: {}", throwable.getMessage());
					}
				});
	}

	@EventListener(classes = OrderCreateRequestEvent.class)
	public void handle(OrderCreateRequestEvent event) {
		log.info("Receive Order created request event: {}", event);
		CompletableFuture.runAsync( () -> this.createOrderUseCase.execute(event.orderId(), event.customerId(), event.products()))
				.whenCompleteAsync((unused, throwable) -> {
					if (throwable != null) {
						log.error("Error creating order: {}", throwable.getMessage());
					}
				});
	}

	@EventListener(classes = OrderTotalCalculatedEvent.class)
	public void handle(OrderTotalCalculatedEvent event) {
		log.info("Receive Order total calculated event: {}", event);
		CompletableFuture.runAsync( () -> this.sendOrderCheckoutUseCase.execute(event.order()))
				.whenCompleteAsync((unused, throwable) -> {
					if (throwable != null) {
						log.error("Error sending order checkout: {}", throwable.getMessage());
					}
				});
	}
}
