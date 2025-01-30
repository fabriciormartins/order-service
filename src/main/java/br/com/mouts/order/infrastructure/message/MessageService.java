package br.com.mouts.order.infrastructure.message;

public interface MessageService {

	<T> void send(T message, String queueName);
}
