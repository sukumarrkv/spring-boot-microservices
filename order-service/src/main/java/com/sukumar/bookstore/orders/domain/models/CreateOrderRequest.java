package com.sukumar.bookstore.orders.domain.models;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record CreateOrderRequest(
		@NotEmpty(message = "Items cannot be empty")
		Set<OrderItem> items,
		@Valid
		Customer customer,
		@Valid
		Address deliveryAddress
		) {

}
