package br.com.mouts.order.domain.repository;

import br.com.mouts.order.domain.model.Order;

import java.util.Optional;

public interface OrderRepository  {

	Optional<Order> findById(String orderId);

	Order save(Order order);
}
