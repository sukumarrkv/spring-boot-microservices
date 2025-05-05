package com.sukumar.bookstore.orders.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sukumar.bookstore.orders.domain.models.CreateOrderEventRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderResponse;

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	private OrderRepository orderRepository;
	private OrderValidator orderValidator;
	private OrderEventService orderEventService;

	public OrderService(OrderRepository orderRepository, OrderValidator orderValidator) {
		this.orderRepository = orderRepository;
		this.orderValidator = orderValidator;
	}
	
	public CreateOrderResponse createOrder(CreateOrderRequest request, String userName) {
		orderValidator.validate(request);
		OrderEntity orderEntity = OrderMapper.convertToEntity(request);
		orderEntity.setUserName(userName);
		OrderEntity savedOrder = orderRepository.save(orderEntity);
		LOGGER.info("Successfully saved order with order number :" + savedOrder.getOrderNumber());
		CreateOrderEventRequest createOrderEventRequest = OrderEventMapper.createOrderEventFromOrderEntity(orderEntity);
		orderEventService.saveOrderEvent(createOrderEventRequest);
		return new CreateOrderResponse(savedOrder.getOrderNumber());
	}
	
}
