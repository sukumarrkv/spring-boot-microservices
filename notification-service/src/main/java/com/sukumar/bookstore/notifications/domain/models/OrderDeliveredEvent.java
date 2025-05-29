package com.sukumar.bookstore.notifications.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDeliveredEvent(
		String orderNumber,
		String eventId,
		Set<OrderItem> items,
		Customer customer,
		Address deliveryAddress,
		LocalDateTime createdAt) {

}
