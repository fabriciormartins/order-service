package br.com.mouts.order.domain.model;

import br.com.mouts.order.domain.event.OrderTotalCalculatedEvent;
import br.com.mouts.order.domain.event.OrderCreatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Document("order")
public class Order extends AbstractAggregateRoot<Order> {

	@Id
	private String id;

	private String customerId;

	private List<Product> products;

	private BigDecimal total;

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

	public BigDecimal getTotal() {
		return total;
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
	public Order(UUID orderId, String customerId, List<Product> products) {
		Objects.requireNonNull(orderId, "Order ID cannot be null");
		Objects.requireNonNull(products, "Products cannot be null");
		Objects.requireNonNull(customerId, "Products cannot be null");
		if (products.isEmpty()) {
			throw new IllegalArgumentException("Products cannot be empty");
		}

		if (customerId.isBlank()) {
			throw new IllegalArgumentException("Customer ID cannot be empty");
		}

		this.id = orderId.toString();
		this.status = OrderStatus.CREATED;
		this.products = products;
		this.customerId = customerId;
		this.total = BigDecimal.ZERO;
		registerEvent(new OrderCreatedEvent(this.id));
	}

	public void calculateTotal() {
		this.total = this.products.stream()
						.map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity().longValue())))
						.reduce(BigDecimal.ZERO, BigDecimal::add);
		this.status = OrderStatus.WAITING_PAYMENT;
		this.registerEvent(new OrderTotalCalculatedEvent(this));
	}
}
