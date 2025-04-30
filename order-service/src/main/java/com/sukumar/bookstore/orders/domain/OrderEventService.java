package com.sukumar.bookstore.orders.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sukumar.bookstore.orders.domain.models.CreateOrderEventRequest;

public class OrderEventService {

	private OrderEventRepository orderEventRepository;
	private ObjectMapper objectMapper;
	
	public OrderEventService(OrderEventRepository orderEventRepository, ObjectMapper objectMapper) {
		this.orderEventRepository = orderEventRepository;
		this.objectMapper = objectMapper;
	}

	public void saveOrderEvent(CreateOrderEventRequest request) {
		OrderEventEntity orderEventEntity = new OrderEventEntity();
		orderEventEntity.setEventId(request.eventId());
		orderEventEntity.setEventType(OrderEventType.ORDER_CREATED);
		orderEventEntity.setOrderNumber(request.orderNumber());
		orderEventEntity.setPayload(convertJsonToString(request));
		orderEventRepository.save(orderEventEntity);
	}
	
	private String convertJsonToString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
