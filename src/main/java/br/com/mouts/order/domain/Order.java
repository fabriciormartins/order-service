package br.com.mouts.order.domain;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

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

    /**
     * @param customerId
     * @param products
     * @return
     */
    public Order create(String customerId, List<Product> products) {
        this.id = java.util.UUID.randomUUID().toString();
        this.status = OrderStatus.CREATED;
        this.products = products;
        this.customerId = customerId;
        this.ammount = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        registerEvent(new OrderCreatedEvent(this.id));
        return this;
    }
}
