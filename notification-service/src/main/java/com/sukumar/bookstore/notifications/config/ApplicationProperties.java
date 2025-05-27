package com.sukumar.bookstore.notifications.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notifications")
public record ApplicationProperties(
		String orderEventsExchange,
		String newOrdersQueue,
		String deliveredOrdersQueue,
		String cancelledOrdersQueue,
		String errorOrdersQueue
		) {

}
