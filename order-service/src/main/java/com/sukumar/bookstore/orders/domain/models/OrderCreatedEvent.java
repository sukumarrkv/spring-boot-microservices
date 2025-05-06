package com.sukumar.bookstore.orders.domain.models;

import java.util.Set;

public record OrderCreatedEvent(
		String orderNumber,
		String eventId,
		Set<OrderItem> items,
		Customer customer,
		Address deliveryAddress) {

}
