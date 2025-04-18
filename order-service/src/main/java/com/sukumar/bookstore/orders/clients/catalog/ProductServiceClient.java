package com.sukumar.bookstore.orders.clients.catalog;

import java.util.Optional;

import org.springframework.web.client.RestClient;

public class ProductServiceClient {

	private final RestClient restClient;
	
	public ProductServiceClient(RestClient restClient) {
		this.restClient = restClient;
	}
	
	public Optional<Product> getProductByCode(String productCode) {
		Product product = restClient.get().uri("/api/products/{code}", productCode).retrieve().body(Product.class);
		return Optional.ofNullable(product);
	}
}
