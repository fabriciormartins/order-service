package br.com.mouts.order.application;

import br.com.mouts.order.UseCase;
import br.com.mouts.order.domain.OrderCreateRequestEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

@UseCase
public class SendOrderRequestEventUseCase {

	private final ApplicationEventPublisher eventPublisher;

	public SendOrderRequestEventUseCase(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public UUID execute(OrderDTO input) {
		UUID orderId = UUID.randomUUID();
		this.eventPublisher.publishEvent(new OrderCreateRequestEvent( orderId ,input.customerId(), input.getProducts()));
		return orderId;
	}
}
