package br.com.mouts.order.application;

import br.com.mouts.order.application.usecase.CalculateTotalOrderUseCase;
import br.com.mouts.order.application.usecase.CreateOrderUseCase;
import br.com.mouts.order.domain.event.OrderCreateRequestEvent;
import br.com.mouts.order.domain.event.OrderCreatedEvent;
import br.com.mouts.order.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {  Config.class, ApplicationEventPublisher.class, OrderEventListener.class })
class OrderEventListenerTest {

	@Autowired
	private ApplicationEventPublisher publisher;

	@MockitoBean
	private CalculateTotalOrderUseCase calculateTotalOrderUseCase;

	@MockitoBean
	private CreateOrderUseCase createOrderUseCase;

	@MockitoSpyBean
	private OrderEventListener orderEventListener;

	@Test
	void handleOrderCreateRequestEvent() {
		var orderId = UUID.randomUUID();
		var customerId = UUID.randomUUID().toString();
		var products = List.of(new Product(UUID.randomUUID().toString(), "Product 1", "Product 1 description", 1, BigDecimal.TEN));
		var event = new OrderCreateRequestEvent(orderId, customerId, products);
		this.publisher.publishEvent(event);

		verify(this.createOrderUseCase).execute(orderId, customerId, products);
	}
	@Test
	void handleOrderCreatedEvent() {
		var orderId = UUID.randomUUID().toString();
		var event = new OrderCreatedEvent(orderId);
		this.publisher.publishEvent(event);

		verify(this.calculateTotalOrderUseCase).execute(orderId);
	}

}

@Configuration
class Config {

	@Bean
	CalculateTotalOrderUseCase calculateTotalOrderUseCase() {
		return mock(CalculateTotalOrderUseCase.class);
	}

	@Bean
	CreateOrderUseCase createOrderUseCase() {
		return mock(CreateOrderUseCase.class);
	}

}