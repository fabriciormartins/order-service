package br.com.mouts.order.application;

import java.math.BigDecimal;

public record ProductDTO(
                String id,
                String name,
                String description,
                Integer quantity,
                BigDecimal price) {
}
