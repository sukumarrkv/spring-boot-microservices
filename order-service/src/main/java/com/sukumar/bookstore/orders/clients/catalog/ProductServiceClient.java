package com.sukumar.bookstore.orders.clients.catalog;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class ProductServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceClient.class);
	private final RestClient restClient;

	public ProductServiceClient(RestClient restClient) {
		this.restClient = restClient;
	}

	@CircuitBreaker(name = "catalog-service")
	@Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallBackMethod")
	public Optional<Product> getProductByCode(String productCode) {
		LOGGER.info("Getting product with code :" + productCode);
		Product product = restClient.get().uri("/api/products/{code}", productCode).retrieve().body(Product.class);
		return Optional.ofNullable(product);
	}
	
	public Optional<Product> getProductByCodeFallBackMethod(String productCode, Throwable t) {
		//We can pass same arguments from method above with Throwable as one more extra argument, this throwable will be used if we to 
		//handle fall back mechanism based on the exceptions
		//One more important is as a developer we should not assume this is right fall back mechanism, we should always get this fallback mechanism
		//from business people. In out case we just return optional of empty, so that order is not placed.
		LOGGER.info("Fall back method executed for getProductByCode method for product with code: "+ productCode);
		return Optional.empty();
	}
}
