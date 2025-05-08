package com.sukumar.bookstore.orders.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderErrorEvent(
		String orderNumber,
		String eventId,
		Set<OrderItem> items,
		Customer customer,
		Address DeliveryAddress,
		LocalDateTime createdAt,
		String reason) {

}
