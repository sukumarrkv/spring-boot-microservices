package com.sukumar.bookstore.notifications.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RabbitMQConfiguration {

	private ApplicationProperties applicationProperties;

	public RabbitMQConfiguration(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(applicationProperties.orderEventsExchange());
	}
	
	@Bean
	Queue newOrderQueue() {
		return QueueBuilder.durable(applicationProperties.newOrdersQueue()).build();
	}
	
	@Bean
	Binding newOrderQueueBinding() {
		return BindingBuilder.bind(newOrderQueue()).to(directExchange()).with(applicationProperties.newOrdersQueue());
	}
	
	@Bean
	Queue deliveredOrdersQueue() {
		return QueueBuilder.durable(applicationProperties.deliveredOrdersQueue()).build();
	}
	
	@Bean
	Binding deliveredOrdersQueueBinding() {
		return BindingBuilder.bind(deliveredOrdersQueue()).to(directExchange()).with(applicationProperties.deliveredOrdersQueue());
	}
	
	@Bean
	Queue cancelledOrdersQueue() {
		return QueueBuilder.durable(applicationProperties.cancelledOrdersQueue()).build();
	}
	
	@Bean
	Binding cancelledOrdersQueueBinding() {
		return BindingBuilder.bind(cancelledOrdersQueue()).to(directExchange()).with(applicationProperties.cancelledOrdersQueue());
	}
	
	@Bean
	Queue errorOrdersQueue() {
		return QueueBuilder.durable(applicationProperties.errorOrdersQueue()).build();
	}
	
	@Bean
	Binding errorOrdersQueueBinding() {
		return BindingBuilder.bind(errorOrdersQueue()).to(directExchange()).with(applicationProperties.errorOrdersQueue());
	}
	
	@Bean
	Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
		return new Jackson2JsonMessageConverter(objectMapper);
	}
	
	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter(objectMapper));
		return rabbitTemplate;
	}
}
