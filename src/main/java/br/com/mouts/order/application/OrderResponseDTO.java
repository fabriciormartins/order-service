package br.com.mouts.order.application;

import br.com.mouts.order.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(String customerId, List<ProductDTO> products, BigDecimal total, OrderStatus status) {
}
