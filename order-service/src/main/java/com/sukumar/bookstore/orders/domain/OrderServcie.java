package com.sukumar.bookstore.orders.domain;

import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderResponse;

public class OrderServcie {

	private OrderRepository orderRepository;

	public OrderServcie(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public CreateOrderResponse createOrder(CreateOrderRequest request, String userName) {
		OrderEntity orderEntity = OrderMapper.convertToEntity(request);
		orderEntity.setUserName(userName);
		OrderEntity savedOrder = orderRepository.save(orderEntity);
		return new CreateOrderResponse(savedOrder.getUserName());
	}
	
}
