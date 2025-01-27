package br.com.mouts.order.application;

import org.springframework.stereotype.Component;

import br.com.mouts.order.domain.OrderCreatedEvent;

@Component
public class OrderEventListener {

    public void handle(OrderCreatedEvent event) {
        System.out.println("Order created: " + event.getOrderId());
    }
}
