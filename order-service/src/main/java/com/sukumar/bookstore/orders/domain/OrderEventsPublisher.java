package com.sukumar.bookstore.orders.domain;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.orders.domain.models.CreateOrderEventRequest;

@Component
public class OrderEventsPublisher {

	private RabbitTemplate rabbitTemplate;
	private ApplicationProperties applicationProperties;
	
	public OrderEventsPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
		this.rabbitTemplate = rabbitTemplate;
		this.applicationProperties = applicationProperties;
	}
	
	public void sendCreateOrderRabbitMessage(CreateOrderEventRequest request) {
		rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), applicationProperties.newOrdersQueue(), request);
	}
}
