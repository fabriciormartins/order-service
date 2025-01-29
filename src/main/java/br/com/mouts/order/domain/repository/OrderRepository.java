package br.com.mouts.order.domain.repository;

import br.com.mouts.order.domain.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

}
