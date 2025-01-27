package br.com.mouts.order.application;

import br.com.mouts.order.UseCase;
import br.com.mouts.order.domain.Order;
import br.com.mouts.order.domain.OrderRepository;

@UseCase
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;

    public CreateOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(OrderDTO input) {
        Order order = new Order();
        this.orderRepository.save(order.create(input.customerId(), input.getProducts()));
    }
}
