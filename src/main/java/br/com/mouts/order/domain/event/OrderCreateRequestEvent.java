package br.com.mouts.order.domain.event;

import br.com.mouts.order.domain.model.Product;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequestEvent(UUID orderId, String customerId, List<Product> products) {
}
