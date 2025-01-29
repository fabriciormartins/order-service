package br.com.mouts.order.domain.service;

import br.com.mouts.order.domain.model.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record OrderCheckoutRequest(String id, String customerId,
								   List<ItemOrderCheckoutRequest> itens,
								   BigDecimal total,
								   OrderStatus status) implements Serializable {
}
