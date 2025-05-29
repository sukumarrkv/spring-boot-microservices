package com.sukumar.bookstore.notifications.domain;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.notifications.domain.models.OrderCreatedEvent;

@Component
public class OrderEventsListener {

	@RabbitListener(queues = "${notifications.new-orders-queue}")
	public void orderCreatedEventListener(OrderCreatedEvent orderCreatedEvent) {
		System.out.println(orderCreatedEvent);
	}
}
