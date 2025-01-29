package br.com.mouts.order.infrastructure.persistence;

import br.com.mouts.order.domain.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderMongoRepository extends MongoRepository<Order, String> {
}
