package com.sukumar.bookstore.orders.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sukumar.bookstore.orders.domain.models.OrderCancelledEvent;
import com.sukumar.bookstore.orders.domain.models.OrderCreatedEvent;
import com.sukumar.bookstore.orders.domain.models.OrderDeliveredEvent;
import com.sukumar.bookstore.orders.domain.models.OrderErrorEvent;
import com.sukumar.bookstore.orders.domain.models.OrderItem;

public class OrderEventMapper {

	public static OrderCreatedEvent buildOrderCreatedEventFromOrderEntity(OrderEntity orderEntity) {
		return new OrderCreatedEvent(
				orderEntity.getOrderNumber(),
				UUID.randomUUID().toString(),
				getOrderItems(orderEntity.getItems()),
				orderEntity.getCustomer(),
				orderEntity.getDeliveryAddress(),
				LocalDateTime.now());
	}
	
	public static OrderDeliveredEvent buildOrderDeliveredEventFromOrderEntity(OrderEntity orderEntity) {
		return new OrderDeliveredEvent(
				orderEntity.getOrderNumber(), 
				UUID.randomUUID().toString(), 
				getOrderItems(orderEntity.getItems()),
				orderEntity.getCustomer(),
				orderEntity.getDeliveryAddress(),
				LocalDateTime.now());
	}
	
	
	public static OrderCancelledEvent buildOrderCancelledEventFromOrderEntity(OrderEntity orderEntity, String reason) {
		return new OrderCancelledEvent(
				orderEntity.getOrderNumber(), 
				UUID.randomUUID().toString(), 
				getOrderItems(orderEntity.getItems()),
				orderEntity.getCustomer(),
				orderEntity.getDeliveryAddress(),
				LocalDateTime.now(), 
				reason);
	}
	
	public static OrderErrorEvent builOrderErrorEventFromOrderEntity(OrderEntity orderEntity, String reason) {
		return new OrderErrorEvent(
				orderEntity.getOrderNumber(), 
				UUID.randomUUID().toString(), 
				getOrderItems(orderEntity.getItems()),
				orderEntity.getCustomer(),
				orderEntity.getDeliveryAddress(),
				LocalDateTime.now(), 
				reason);
	}
	
	private static Set<OrderItem> getOrderItems(Set<OrderItemEntity> itemEntities) {
		return itemEntities.stream().map(eachItem -> new OrderItem(
				eachItem.getCode(), 
				eachItem.getName(), 
				eachItem.getPrice(), 
				eachItem.getQuantity())).collect(Collectors.toSet());
	}
}
