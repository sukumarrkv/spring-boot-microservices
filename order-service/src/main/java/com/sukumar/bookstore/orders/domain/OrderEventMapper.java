package com.sukumar.bookstore.orders.domain;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sukumar.bookstore.orders.domain.models.CreateOrderEventRequest;
import com.sukumar.bookstore.orders.domain.models.OrderItem;

public class OrderEventMapper {

	public static CreateOrderEventRequest createOrderEventFromOrderEntity(OrderEntity orderEntity) {
		return new CreateOrderEventRequest(
				orderEntity.getOrderNumber(),
				UUID.randomUUID().toString(),
				getOrderItems(orderEntity.getItems()),
				orderEntity.getCustomer(),
				orderEntity.getDeliveryAddress());
	}
	
	private static Set<OrderItem> getOrderItems(Set<OrderItemEntity> itemEntities) {
		return itemEntities.stream().map(eachItem -> new OrderItem(
				eachItem.getCode(), 
				eachItem.getName(), 
				eachItem.getPrice(), 
				eachItem.getQuantity())).collect(Collectors.toSet());
	}
}
