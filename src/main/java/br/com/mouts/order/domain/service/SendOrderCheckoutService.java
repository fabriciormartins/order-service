package br.com.mouts.order.domain.service;

import br.com.mouts.order.domain.model.Order;

public interface SendOrderCheckoutService {

	void send(Order order);
}
