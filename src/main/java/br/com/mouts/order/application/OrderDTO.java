package br.com.mouts.order.application;

import java.util.List;

import br.com.mouts.order.domain.Product;

public record OrderDTO(String id,
                String customerId,
                List<ProductDTO> products) {

        public List<Product> getProducts() {
                return products.stream()
                                .map(product -> new Product(product.id(), product.name(), product.description(),
                                                product.quantity(), product.price()))
                                .toList();
        }
}
