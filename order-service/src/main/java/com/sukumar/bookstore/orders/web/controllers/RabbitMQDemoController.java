package com.sukumar.bookstore.orders.web.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sukumar.bookstore.orders.domain.ApplicationProperties;

@RestController
public class RabbitMQDemoController {

	private RabbitTemplate rabbitTemplate;
	private ApplicationProperties applicationProperties;
	
	
	
	public RabbitMQDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.applicationProperties = applicationProperties;
	}

	@PostMapping("/send")
	public void sendMessage(@RequestBody MyMessage myMessage) {
		rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), 
				                      myMessage.routingKey(), myMessage.message());
	}
	
	record MyMessage(String routingKey, String message) {}
}
