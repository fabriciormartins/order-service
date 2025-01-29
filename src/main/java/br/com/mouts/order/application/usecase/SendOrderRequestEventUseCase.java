package br.com.mouts.order.application.usecase;

import br.com.mouts.order.application.OrderDTO;
import br.com.mouts.order.domain.event.OrderCreateRequestEvent;
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
