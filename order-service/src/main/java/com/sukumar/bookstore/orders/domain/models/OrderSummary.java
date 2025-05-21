package com.sukumar.bookstore.orders.domain.models;

public record OrderSummary(
		String orderNumber,
		OrderStatus orderStatus
		) {

}
