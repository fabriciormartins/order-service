package br.com.mouts.order.domain.service;

import java.io.Serializable;
import java.math.BigDecimal;

public record ItemOrderCheckoutRequest(
		String id,
		String nome,
		String description,
		Integer quantity,
		BigDecimal unitValue
) implements Serializable {
}
