package com.sukumar.bookstore.notifications.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderCreatedEvent(
		String orderNumber,
		String eventId,
		Set<OrderItem> items,
		Customer customer,
		Address deliveryAddress,
		LocalDateTime createdAt) {

}
