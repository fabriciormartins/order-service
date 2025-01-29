package br.com.mouts.order.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;


@OpenAPIDefinition(
		info = @Info (title = "Order API", version = "1.0", description = "Order API"),
		tags = @io.swagger.v3.oas.annotations.tags.Tag(name = "Order API")
)
@RequestMapping(value = "/orders")
@RestController
public class OrderController {

	private final SendOrderRequestEventUseCase sendOrderRequestEventUseCase;
	private final FindOrderByIdUseCase findOrderByIdUseCase;

	public OrderController(SendOrderRequestEventUseCase sendOrderRequestEventUseCase, FindOrderByIdUseCase findOrderByIdUseCase) {
		this.sendOrderRequestEventUseCase = sendOrderRequestEventUseCase;
		this.findOrderByIdUseCase = findOrderByIdUseCase;
	}

	@Operation(summary = "Create a new order", tags = {"Order API"}, description = "Create a new order")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createOrder(@RequestBody OrderDTO orderDTO) {
		UUID orderId = this.sendOrderRequestEventUseCase.execute(orderDTO);
		return ResponseEntity.accepted().location(URI.create("/orders/%s".formatted(orderId.toString()))).build();
	}

	@Operation(summary = "Get order by id", tags = {"Order API"},  description = "Get order by id")
	@GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String orderId) {
		return ResponseEntity.ok(this.findOrderByIdUseCase.execute(orderId));
	}
}
