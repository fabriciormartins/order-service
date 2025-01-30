package br.com.mouts.order.infrastructure.message;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

	@Bean
	public Queue orderCheckoutQueue(@Value("queue.order-checkout") String queueName) {
		return new Queue(queueName, true);
	}

}
