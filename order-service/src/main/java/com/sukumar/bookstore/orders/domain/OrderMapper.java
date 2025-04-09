package com.sukumar.bookstore.orders.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.OrderItem;
import com.sukumar.bookstore.orders.domain.models.OrderStatus;

public class OrderMapper {

	public static OrderEntity convertToEntity(CreateOrderRequest request) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderNumber(UUID.randomUUID().toString());
		orderEntity.setStatus(OrderStatus.NEW);
		orderEntity.setCustomer(request.customer());
		orderEntity.setDeliveryAddress(request.deliveryAddress());
		Set<OrderItemEntity> orderItems = new HashSet<>();
		
		for(OrderItem item : request.items()) {
			OrderItemEntity orderItemEntity = new OrderItemEntity();
			orderItemEntity.setCode(item.code());
			orderItemEntity.setName(item.name());
			orderItemEntity.setPrice(item.price());
			orderItemEntity.setQuantity(item.quantity());
			orderItemEntity.setOrder(orderEntity);
			orderItems.add(orderItemEntity);
		}
		
		orderEntity.setItems(orderItems);
		return orderEntity;
	}
}
