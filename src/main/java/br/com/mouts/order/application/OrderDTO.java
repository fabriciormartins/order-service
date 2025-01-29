package br.com.mouts.order.application;

import br.com.mouts.order.domain.model.Product;

import java.util.List;

public record OrderDTO(String customerId,
					   List<ProductDTO> products) {

	public List<Product> getProducts() {
		return products.stream()
				.map(product -> new Product(product.id(), product.name(), product.description(),
						product.quantity(), product.price()))
				.toList();
	}

}
