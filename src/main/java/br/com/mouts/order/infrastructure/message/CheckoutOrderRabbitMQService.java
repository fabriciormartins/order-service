package br.com.mouts.order.infrastructure.message;

import br.com.mouts.order.domain.model.Order;
import br.com.mouts.order.domain.service.ItemOrderCheckoutRequest;
import br.com.mouts.order.domain.service.OrderCheckoutRequest;
import br.com.mouts.order.domain.service.SendOrderCheckoutService;
//import br.com.mouts.order.infrastructure.external.service.CheckoutOrderFeignService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CheckoutOrderRabbitMQService implements SendOrderCheckoutService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CheckoutOrderRabbitMQService.class);

	private final MessageService messageService;

//	private final CheckoutOrderFeignService checkoutOrderFeignService;

	@Value("queue.order-checkout")
	private String queueName;

	public CheckoutOrderRabbitMQService(MessageService messageService
//										,CheckoutOrderFeignService checkoutOrderFeignService
	) {
		this.messageService = messageService;
//		this.checkoutOrderFeignService = checkoutOrderFeignService;
	}

	public void send(Order order) {
		List<ItemOrderCheckoutRequest> items = order.getProducts()
				.stream()
				.map(product ->
						new ItemOrderCheckoutRequest(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice()))
				.collect(Collectors.toList());
		OrderCheckoutRequest orderRequest = new OrderCheckoutRequest(order.getId(), order.getCustomerId(), items, order.getTotal(), order.getStatus());
//		if(this.checkoutOrderFeignService.isAvaliable()){
//			this.checkoutOrderFeignService.execute(orderRequest);
//			log.info("[HTTP] Order sent to checkout: {}", orderRequest);
//			return;
//		}
		try {
			this.messageService.send(orderRequest, this.queueName);
			log.info("[RABBIT] Order sent to checkout: {}", orderRequest);
		}catch (Exception e){
			e.printStackTrace();
			log.error("Error sending order to checkout: {}", e.getMessage());
		}
	}
}
