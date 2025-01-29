package br.com.mouts.order.infrastructure.persistence;

import br.com.mouts.order.domain.model.Order;
import br.com.mouts.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoOrderRepository implements OrderRepository {

	private final OrderMongoRepository orderMongoRepository;

	public MongoOrderRepository(OrderMongoRepository orderMongoRepository) {
		this.orderMongoRepository = orderMongoRepository;
	}

	@Override
	public Optional<Order> findById(String orderId) {
		return this.orderMongoRepository.findById(orderId);
	}

	@Override
	public Order save(Order order) {
		return this.orderMongoRepository.save(order);
	}
}
