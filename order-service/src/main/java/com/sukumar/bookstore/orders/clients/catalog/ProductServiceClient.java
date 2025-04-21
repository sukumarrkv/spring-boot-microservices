package com.sukumar.bookstore.orders.clients.catalog;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceClient.class);
	private final RestClient restClient;

	public ProductServiceClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public Optional<Product> getProductByCode(String productCode) {
		try {
			LOGGER.info("Getting product with code :" + productCode);
			Product product = restClient.get().uri("/api/products/{code}", productCode).retrieve().body(Product.class);
			return Optional.ofNullable(product);
		} catch (Exception e) {
			LOGGER.info("Error occurred while getting product with code :" + productCode);
			LOGGER.error("Error occurred while getting product with code :" + e.getMessage());
			return Optional.empty();
		}
	}
}
