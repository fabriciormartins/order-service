package br.com.mouts.order.application;

import br.com.mouts.order.UseCase;
import br.com.mouts.order.domain.OrderCreateRequestEvent;
import org.springframework.context.ApplicationEventPublisher;

@UseCase
public class CreateOrderUseCase {

	private final ApplicationEventPublisher eventPublisher;

	public CreateOrderUseCase(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public void execute(OrderDTO input) {
		this.eventPublisher.publishEvent(new OrderCreateRequestEvent(input.customerId(), input.getProducts()));
	}
}
