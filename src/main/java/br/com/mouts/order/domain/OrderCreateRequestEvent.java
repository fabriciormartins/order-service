package br.com.mouts.order.domain;

import java.util.List;

public record OrderCreateRequestEvent(String customerId, List<Product> products) {
}
