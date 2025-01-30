package br.com.mouts.order.infrastructure.external.service;

import br.com.mouts.order.domain.service.OrderCheckoutRequest;
import org.springframework.stereotype.Component;


@Component
public class CheckoutOrderFeignService {

	private final FeignClientCheckouOrderService feignClientCheckouOrderService;

	public CheckoutOrderFeignService(FeignClientCheckouOrderService feignClientCheckouOrderService) {
		this.feignClientCheckouOrderService = feignClientCheckouOrderService;
	}

	public void execute(OrderCheckoutRequest orderRequest) {
		this.feignClientCheckouOrderService.execute(orderRequest);
	}

	public boolean isAvaliable() {
		try {
			var response = this.feignClientCheckouOrderService.actuatorHealth();
			return response.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			return false;
		}
	}
}
