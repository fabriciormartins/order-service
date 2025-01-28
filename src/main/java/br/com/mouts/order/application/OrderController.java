package br.com.mouts.order.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.concurrent.Callable;

@RequestMapping(value = "/orders")
@RestController
public class OrderController {

	private final CreateOrderUseCase createOrderUseCase;

	public OrderController(CreateOrderUseCase createOrderUseCase) {
		this.createOrderUseCase = createOrderUseCase;
	}

	@PostMapping()
	public ResponseEntity<Void> createOrder(@RequestBody OrderDTO orderDTO) {
		this.createOrderUseCase.execute(orderDTO);
		return ResponseEntity.accepted().build();
	}

}
