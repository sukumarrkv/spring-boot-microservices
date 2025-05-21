package com.sukumar.bookstore.orders.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
		String orderNumber,
		String user,
		OrderStatus orderstatus,
		Customer customer,
		Address deliveryAddress,
		Set<OrderItem> items,
		String comments,
		LocalDateTime createdAt		
		) {

	public BigDecimal getTotalAmount() {
		return items.stream().map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
	}
}
