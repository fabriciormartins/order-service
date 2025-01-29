package br.com.mouts.order.load.simulations;

import br.com.mouts.order.application.OrderDTO;
import br.com.mouts.order.application.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static io.gatling.javaapi.core.CoreDsl.*;

public class OrderCreateSimulation extends Simulation {

	private static final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
	private List<ProductDTO> products;
	private String customerId;
	private String json;

	@Override
	public void before() {
		this.products = IntStream.range(0, 1000)
				.mapToObj(i -> new ProductDTO(UUID.randomUUID().toString(),
						"Nome Product %d".formatted(i),
						"Descrição %d do produto".formatted(i), 10,
						BigDecimal.TEN)).toList();
		this.customerId = UUID.randomUUID().toString();
		Supplier<String> getJson = () -> {
			try {
				OrderDTO dto = new OrderDTO(customerId, products);
				return mapper.writeValueAsString(dto);
			} catch (Exception e) {
				System.err.println(e);
				return "{}";
			}};
		this.json = getJson.get();
	}

	ScenarioBuilder scenario = scenario("Create orders from 50 to 2500 users in six seconds")
			.exec(HttpDsl.http("POST /orders")
					.post("/orders")
					.body(StringBody(session -> this.json)));

	{
		HttpProtocolBuilder httpProtocol = HttpDsl.http.baseUrl("http://localhost:8080")
				.header("Accept", MediaType.APPLICATION_JSON_VALUE)
				.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		setUp(scenario.injectOpen(rampUsersPerSec(50).to(2500).during(Duration.ofSeconds(6)))).protocols(httpProtocol);
	}
}
