package com.sukumar.bookstore.orders.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderResponse;
import com.sukumar.bookstore.orders.domain.models.OrderCreatedEvent;
import com.sukumar.bookstore.orders.domain.models.OrderStatus;

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "UK", "GERMANY");
	
	private OrderRepository orderRepository;
	private OrderValidator orderValidator;
	private OrderEventService orderEventService;

	public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {
		this.orderRepository = orderRepository;
		this.orderValidator = orderValidator;
		this.orderEventService = orderEventService;
	}
	
	public CreateOrderResponse createOrder(CreateOrderRequest request, String userName) {
		orderValidator.validate(request);
		OrderEntity orderEntity = OrderMapper.convertToEntity(request);
		orderEntity.setUserName(userName);
		OrderEntity savedOrder = orderRepository.save(orderEntity);
		LOGGER.info("Successfully saved order with order number :" + savedOrder.getOrderNumber());
		OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEventFromOrderEntity(orderEntity);
		orderEventService.saveOrderEvent(orderCreatedEvent);
		return new CreateOrderResponse(savedOrder.getOrderNumber());
	}
	
	public void processNewOrders() {
		List<OrderEntity> newOrders = orderRepository.findByStatus(OrderStatus.NEW);
		LOGGER.info("Found {} new order to process.", newOrders.size());
		for(OrderEntity order: newOrders) {
			processEachNewOrder(order);
		}
	}
	
	private void processEachNewOrder(OrderEntity orderEntity) {
		if(isValidDeliveryCountry(orderEntity.getDeliveryAddress().country())) {
			LOGGER.info("Order {} is successfully delivered", orderEntity.getOrderNumber());
		}
	}
	
	private boolean isValidDeliveryCountry(String country) {
		return DELIVERY_ALLOWED_COUNTRIES.contains(country.toUpperCase());
	}
}
