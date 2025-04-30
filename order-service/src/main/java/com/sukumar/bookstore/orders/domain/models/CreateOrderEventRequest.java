package com.sukumar.bookstore.orders.domain.models;

import java.util.Set;

public record CreateOrderEventRequest(
		String orderNumber,
		String eventId,
		Set<OrderItem> items,
		Customer customer,
		Address deliveryAddress) {

}
