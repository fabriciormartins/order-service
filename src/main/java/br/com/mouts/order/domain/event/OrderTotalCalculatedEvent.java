package br.com.mouts.order.domain.event;

import br.com.mouts.order.domain.model.Order;

public record OrderTotalCalculatedEvent(Order order) {
}
