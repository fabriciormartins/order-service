package br.com.mouts.order.domain;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequestEvent(UUID orderId, String customerId, List<Product> products) {
}
