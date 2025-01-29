package br.com.mouts.order.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

	private final String id;
	private final String name;
	private final String description;
	private final Integer quantity;
	private final BigDecimal price;

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

		if (id.isBlank()) {
			throw new IllegalArgumentException("Id cannot be empty or blank");
		}
		if (name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be empty or blank");
		}
		if (description.isBlank()) {
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
