package br.com.mouts.order.infrastructure.external.service;

import br.com.mouts.order.domain.service.OrderCheckoutRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service", url = "${checkout-order-service.url}")
public interface FeignClientCheckouOrderService {

	@PostMapping(path = "/checkout")
	ResponseEntity<Void> execute(OrderCheckoutRequest orderRequest);

	@GetMapping(path = "/actuator/health")
	ResponseEntity<String> actuatorHealth();



}
