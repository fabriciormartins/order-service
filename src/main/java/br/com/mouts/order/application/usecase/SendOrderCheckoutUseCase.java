package br.com.mouts.order.application.usecase;


import br.com.mouts.order.domain.model.Order;
import br.com.mouts.order.domain.service.SendOrderCheckoutService;

import java.util.Objects;

@UseCase
public class SendOrderCheckoutUseCase {

	private final SendOrderCheckoutService sendOrderCheckoutService;

	public SendOrderCheckoutUseCase(SendOrderCheckoutService sendOrderCheckoutService) {
		this.sendOrderCheckoutService = sendOrderCheckoutService;
	}

	public void execute(Order order) {
		Objects.requireNonNull(order, "Order cannot be null");
		this.sendOrderCheckoutService.send(order);
	}
}
