package com.sukumar.bookstore.orders.domain.models;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItem(
		@NotBlank(message = "Code is required")
		String code,
		@NotBlank(message = "Name is required")
		String name,
		@NotNull(message = "Price is required")
		BigDecimal price,
		@NotNull(message = "Quantity is required")
		@Min(1)
		Integer quantity
		) {

}
