package br.com.mouts.order.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    /**
     * @param id
     * @param name
     * @param description
     * @param quantity
     * @param price
     */
    public Product(
            String id,
            String name,
            String description,
            Integer quantity,
            BigDecimal price) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(description, "Description cannot be null");
        Objects.requireNonNull(quantity, "Quantity cannot be null");
        Objects.requireNonNull(price, "Price cannot be null");

        if (id.isEmpty() || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be empty or blank");
        }
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty or blank");
        }
        if (description.isEmpty() || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty or blank");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
