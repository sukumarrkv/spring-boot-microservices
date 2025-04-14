package com.sukumar.bookstore.orders.domain;

import org.springframework.stereotype.Service;

import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderResponse;

@Service
public class OrderService {

	private OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public CreateOrderResponse createOrder(CreateOrderRequest request, String userName) {
		OrderEntity orderEntity = OrderMapper.convertToEntity(request);
		orderEntity.setUserName(userName);
		OrderEntity savedOrder = orderRepository.save(orderEntity);
		return new CreateOrderResponse(savedOrder.getOrderNumber());
	}
	
}
