package com.sukumar.bookstore.orders.domain;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.orders.domain.models.OrderCancelledEvent;
import com.sukumar.bookstore.orders.domain.models.OrderCreatedEvent;
import com.sukumar.bookstore.orders.domain.models.OrderDeliveredEvent;
import com.sukumar.bookstore.orders.domain.models.OrderErrorEvent;

@Component
public class OrderEventsPublisher {

	private RabbitTemplate rabbitTemplate;
	private ApplicationProperties applicationProperties;
	
	public OrderEventsPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
		this.rabbitTemplate = rabbitTemplate;
		this.applicationProperties = applicationProperties;
	}
	
	public void sendOrderCreatedRabbitMessage(OrderCreatedEvent event) {
		sendRabbitMessage(applicationProperties.newOrdersQueue(), event);
	}
	
	public void sendOrderDeliveredRabbitMessage(OrderDeliveredEvent event) {
		sendRabbitMessage(applicationProperties.deliveredOrdersQueue(), event);
	}
	
	public void sendOrderCancelledRabbitMessage(OrderCancelledEvent event) {
		sendRabbitMessage(applicationProperties.cancelledOrdersQueue(), event);
	}
	
	public void sendOrderErrorRabbitMessage(OrderErrorEvent event) {
		sendRabbitMessage(applicationProperties.errorOrdersQueue(), event);
	}
	
	private void sendRabbitMessage(String routingKey, Object object) {
		rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), routingKey, object);
	}
}
