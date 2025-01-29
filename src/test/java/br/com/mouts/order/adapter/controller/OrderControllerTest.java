package br.com.mouts.order.adapter.controller;

import br.com.mouts.order.application.OrderDTO;
import br.com.mouts.order.application.OrderResponseDTO;
import br.com.mouts.order.application.ProductDTO;
import br.com.mouts.order.application.usecase.SendOrderRequestEventUseCase;
import br.com.mouts.order.application.exception.GlobalExceptionHandler;
import br.com.mouts.order.application.usecase.FindOrderByIdUseCase;
import br.com.mouts.order.domain.model.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;


	private SendOrderRequestEventUseCase sendOrderRequestEventUseCase;

	private FindOrderByIdUseCase findOrderByIdUseCase;

	@BeforeAll
	void setUp() {
		this.objectMapper = new ObjectMapper().findAndRegisterModules();
		this.sendOrderRequestEventUseCase = Mockito.mock(SendOrderRequestEventUseCase.class);
		this.findOrderByIdUseCase = Mockito.mock(FindOrderByIdUseCase.class);
		OrderController orderController = new OrderController(this.sendOrderRequestEventUseCase, this.findOrderByIdUseCase);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	void createOrder() throws Exception {
		// Given
		UUID orderId = UUID.randomUUID();
		when(this.sendOrderRequestEventUseCase.execute(any(OrderDTO.class))).thenReturn(orderId);
		String customerId = UUID.randomUUID().toString();
		List<ProductDTO> products = List.of(new ProductDTO(UUID.randomUUID().toString(), "Product 1", "Product 1 description", 2, BigDecimal.TEN));
		OrderDTO orderDTO = new OrderDTO(customerId, products);
		// When
		this.mockMvc.perform(post("/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(orderDTO))
				)
				// Then
				.andExpect(status().isAccepted())
				.andExpect(header().exists("Location"))
				.andExpect(header().string("Location", "/orders/%s".formatted(orderId.toString())));

	}

	@Test
	void getOrderById() throws Exception {
		// Given
		UUID orderId = UUID.randomUUID();
		String customerId = UUID.randomUUID().toString();
		List<ProductDTO> products = List.of(new ProductDTO(UUID.randomUUID().toString(), "Product 1", "Product 1 description", 2, BigDecimal.TEN));
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO(customerId, products, BigDecimal.TEN, OrderStatus.CREATED);
		when(this.findOrderByIdUseCase.execute(orderId.toString())).thenReturn(orderResponseDTO);
		// When
		ProductDTO product = products.getFirst();
		this.mockMvc.perform(get("/orders/%s".formatted(orderId.toString()))
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(orderResponseDTO))
				)
				// Then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.customerId").value(customerId))
				.andExpect(jsonPath("$.products").isArray())
				.andExpect(jsonPath("$.products[0].id").value(product.id()))
				.andExpect(jsonPath("$.products[0].name").value(product.name()))
				.andExpect(jsonPath("$.products[0].description").value(product.description()))
				.andExpect(jsonPath("$.products[0].quantity").value(product.quantity()))
				.andExpect(jsonPath("$.products[0].price").value(product.price()));
	}
}