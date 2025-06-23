package com.sukumar.bookstore.orders.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sukumar.bookstore.orders.domain.ApplicationProperties;

@Configuration
public class RabbitMQConfig {

	private ApplicationProperties applicationProperties;

	public RabbitMQConfig(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(applicationProperties.orderEventsExchange());
	}
	
	//For all below queue routing key name is same as the queue name 
	
	@Bean
	Queue newOrdersQueue() {
		return QueueBuilder.durable(applicationProperties.newOrdersQueue()).build();
	}
	
	@Bean
	Binding newOrdersQueueBinding() {
		return BindingBuilder.bind(newOrdersQueue()).to(directExchange()).with(applicationProperties.newOrdersQueue());
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }
}
