package br.com.mouts.order.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Document("order")
public class Order extends AbstractAggregateRoot<Order> {

	@Id
	private String id;

	private String customerId;

	private List<Product> products;

	private BigDecimal ammount;

	private OrderStatus status;

	public String getId() {
		return id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public Order() {
	}

	/**
	 * @param customerId
	 * @param products
	 * @return
	 */
	public Order(String customerId, List<Product> products) {
		Objects.requireNonNull(products, "Products cannot be null");
		Objects.requireNonNull(customerId, "Products cannot be null");
		if (products.isEmpty()) {
			throw new IllegalArgumentException("Products cannot be empty");
		}

		if (customerId.isBlank()) {
			throw new IllegalArgumentException("Customer ID cannot be empty");
		}

		this.id = java.util.UUID.randomUUID().toString();
		this.status = OrderStatus.CREATED;
		this.products = products;
		this.customerId = customerId;
		this.ammount = BigDecimal.ZERO;
		registerEvent(new OrderCreatedEvent(this.id));
	}

	public void calculateAmmount() {
		this.ammount = this.products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.status  = OrderStatus.WAITING_PAYMENT;
		this.registerEvent(new OrderAmmountCalculatedEvent(this));
	}
}
