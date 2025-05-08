package com.sukumar.bookstore.orders.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderCancelledEvent(
		String orderNumber,
		String eventId,
		Set<OrderItem> items,
		Customer customer,
		Address deliveryAddress,
		LocalDateTime createdAt,
		String reason) {

}
