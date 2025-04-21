package com.sukumar.bookstore.orders.domain;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.orders.clients.catalog.Product;
import com.sukumar.bookstore.orders.clients.catalog.ProductServiceClient;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.OrderItem;
import com.sukumar.bookstore.orders.web.exception.InvalidOrderException;

@Component
public class OrderValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidator.class);

	private ProductServiceClient productServiceClient;

	public OrderValidator(ProductServiceClient productServiceClient) {
		this.productServiceClient = productServiceClient;
	}
	
	public void validate(CreateOrderRequest orderRequest) {
		Set<OrderItem> items = orderRequest.items();
		
		for(OrderItem item : items) {
			Product product = productServiceClient.getProductByCode(item.code())
					          .orElseThrow(() -> new InvalidOrderException("Invalid Product code :" + item.code()));
			
			if(item.price().compareTo(product.price()) != 0) {
				LOGGER.error("Product prices are not matching. Actual price is {}, received price is {}", product.price(), item.price());
				throw new InvalidOrderException("Invalid Product price. Product price are not matching with original product");
			}
		}
	}
}
