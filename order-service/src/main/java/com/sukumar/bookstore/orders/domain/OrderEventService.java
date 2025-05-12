package com.sukumar.bookstore.orders.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sukumar.bookstore.orders.domain.models.OrderCancelledEvent;
import com.sukumar.bookstore.orders.domain.models.OrderCreatedEvent;
import com.sukumar.bookstore.orders.domain.models.OrderDeliveredEvent;
import com.sukumar.bookstore.orders.domain.models.OrderErrorEvent;

@Service
public class OrderEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventService.class);
	
	private OrderEventRepository orderEventRepository;
	private ObjectMapper objectMapper;
	private OrderEventsPublisher orderEventsPublisher;
	
	public OrderEventService(OrderEventRepository orderEventRepository, ObjectMapper objectMapper, OrderEventsPublisher orderEventsPublisher) {
		this.orderEventRepository = orderEventRepository;
		this.objectMapper = objectMapper;
		this.orderEventsPublisher = orderEventsPublisher;
	}

	public void saveOrderCreatedEvent(OrderCreatedEvent event) {
		OrderEventEntity orderEventEntity = new OrderEventEntity();
		orderEventEntity.setEventId(event.eventId());
		orderEventEntity.setEventType(OrderEventType.ORDER_CREATED);
		orderEventEntity.setOrderNumber(event.orderNumber());
		orderEventEntity.setPayload(convertJsonToString(event));
		orderEventRepository.save(orderEventEntity);
	}
	
	public void saveOrderDeliveredEvent(OrderDeliveredEvent event) {
		OrderEventEntity orderEventEntity = new OrderEventEntity();
		orderEventEntity.setEventId(event.eventId());
		orderEventEntity.setEventType(OrderEventType.ORDER_DELIVERED);
		orderEventEntity.setOrderNumber(event.orderNumber());
		orderEventEntity.setPayload(convertJsonToString(event));
		orderEventRepository.save(orderEventEntity);
	}
	
	public void saveOrderCancelledEvent(OrderCancelledEvent event) {
		OrderEventEntity orderEventEntity = new OrderEventEntity();
		orderEventEntity.setEventId(event.eventId());
		orderEventEntity.setEventType(OrderEventType.ORDER_CANCELLED);
		orderEventEntity.setOrderNumber(event.orderNumber());
		orderEventEntity.setPayload(convertJsonToString(event));
		orderEventRepository.save(orderEventEntity);
	}
	
	public void saveOrderErrorEvent(OrderErrorEvent event) {
		OrderEventEntity orderEventEntity = new OrderEventEntity();
		orderEventEntity.setEventId(event.eventId());
		orderEventEntity.setEventType(OrderEventType.ORDER_PROCESSING_FAILED);
		orderEventEntity.setOrderNumber(event.orderNumber());
		orderEventEntity.setPayload(convertJsonToString(event));
		orderEventRepository.save(orderEventEntity);
	}
	public void publishOrderEvents() {
		Sort sort = Sort.by("createdAt").ascending();
		List<OrderEventEntity> orderEvents = orderEventRepository.findAll(sort);
		LOGGER.info("Found {} order events to be published", orderEvents.size());
		for(OrderEventEntity event: orderEvents) {
			publishEvent(event);
			orderEventRepository.delete(event);
		}
	}
	
	private String convertJsonToString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void publishEvent(OrderEventEntity orderEventEntity) {
		OrderEventType eventType = orderEventEntity.getEventType();
		switch(eventType) {
		case ORDER_CREATED:
			OrderCreatedEvent orderCreatedEvent = convertStringToJson(orderEventEntity.getPayload(), OrderCreatedEvent.class);
			orderEventsPublisher.sendOrderCreatedRabbitMessage(orderCreatedEvent);
			break;
		case ORDER_DELIVERED:
			OrderDeliveredEvent orderDeliveredEvent = convertStringToJson(orderEventEntity.getPayload(), OrderDeliveredEvent.class);
			orderEventsPublisher.sendOrderDeliveredRabbitMessage(orderDeliveredEvent);
			break;
		case ORDER_CANCELLED:
			OrderCancelledEvent orderCancelledEvent = convertStringToJson(orderEventEntity.getPayload(), OrderCancelledEvent.class);
			orderEventsPublisher.sendOrderCancelledRabbitMessage(orderCancelledEvent);
			break;
		case ORDER_PROCESSING_FAILED:
			OrderErrorEvent orderErrorEvent = convertStringToJson(orderEventEntity.getPayload(), OrderErrorEvent.class);
			orderEventsPublisher.sendOrderErrorRabbitMessage(orderErrorEvent);
			break;
		default: 
			LOGGER.warn("Unsupported Event Type: " + eventType);
		}
	}
	
	private <T> T convertStringToJson(String payload, Class<T> clazz) {
		try {
			return objectMapper.readValue(payload, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
