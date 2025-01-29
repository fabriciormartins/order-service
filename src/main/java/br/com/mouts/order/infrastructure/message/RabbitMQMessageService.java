package br.com.mouts.order.infrastructure.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class RabbitMQMessageService implements MessageService{

	private final RabbitTemplate rabbitTemplate;

	public RabbitMQMessageService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}


	@Override
	public <T> void send(T message, String queueName) {
		Objects.requireNonNull(message, "message cannot be null");
		Objects.requireNonNull(queueName, "queue cannot be null");
		this.rabbitTemplate.convertAndSend(queueName, message);
	}
}
