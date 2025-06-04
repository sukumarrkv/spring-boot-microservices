package com.sukumar.bookstore.notifications.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.notifications.domain.models.OrderCancelledEvent;
import com.sukumar.bookstore.notifications.domain.models.OrderCreatedEvent;
import com.sukumar.bookstore.notifications.domain.models.OrderDeliveredEvent;
import com.sukumar.bookstore.notifications.domain.models.OrderErrorEvent;

@Component
public class OrderEventsListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsListener.class);
	
	private NotificationService notificationService;
	
	private OrderEventsRepository orderEventsRepository;
	
	public OrderEventsListener(NotificationService notificationService, OrderEventsRepository orderEventsRepository) {
		super();
		this.notificationService = notificationService;
		this.orderEventsRepository = orderEventsRepository;
	}

	@RabbitListener(queues = "${notifications.new-orders-queue}")
	public void orderCreatedEventListener(OrderCreatedEvent orderCreatedEvent) {
		LOGGER.info("Received OrderCreatedEvent :"+ orderCreatedEvent);
		String eventId = orderCreatedEvent.eventId();
		
		if(orderEventsRepository.existsByEventId(eventId)) {
			LOGGER.info("Received duplicate orderCreatedEvent with event Id: " + eventId);
			return;
		}
		
		notificationService.sendOrderCreatedEmailNotification(orderCreatedEvent);
		
		OrderEventsEntity orderEventsEntity = new OrderEventsEntity(eventId);
		orderEventsRepository.save(orderEventsEntity);
	}
	
	@RabbitListener(queues = "${notifications.delivered-orders-queue}")
	public void orderDeliveredEventListener(OrderDeliveredEvent orderDeliveredEvent) {
		LOGGER.info("Received OrderDeliveredEvent :"+ orderDeliveredEvent);
		String eventId = orderDeliveredEvent.eventId();
		
		if(orderEventsRepository.existsByEventId(eventId)) {
			LOGGER.info("Received duplicate OrderDeliveredEvent with event Id: "+ eventId);
			return;
		}
		
		notificationService.sendOrderDeliveredEmailNotification(orderDeliveredEvent);
		
		OrderEventsEntity orderEventsEntity = new OrderEventsEntity(eventId);
		orderEventsRepository.save(orderEventsEntity);
	}
	
	@RabbitListener(queues = "${notifications.cancelled-orders-queue}")
	public void orderCancelledEventListener(OrderCancelledEvent orderCancelledEvent) {
		LOGGER.info("Received OrderCancelledEvent :"+ orderCancelledEvent);
		String eventId = orderCancelledEvent.eventId();
		
		if(orderEventsRepository.existsByEventId(eventId)) {
			LOGGER.info("Received duplicate OrderCancelledEvent with event Id: "+ eventId);
			return;
		}
		
		notificationService.sendOrderCancelledEmailNotification(orderCancelledEvent);
		
		OrderEventsEntity orderEventsEntity = new OrderEventsEntity(eventId);
		orderEventsRepository.save(orderEventsEntity);
	}
	
	@RabbitListener(queues = "${notifications.error-orders-queue}")
	public void orderErrorEventListener(OrderErrorEvent orderErrorEvent) {
		LOGGER.info("Received OrderErrorEvent :"+ orderErrorEvent);
		String eventId = orderErrorEvent.eventId();
		
		if(orderEventsRepository.existsByEventId(eventId)) {
			LOGGER.info("Received duplicate OrderErrorEvent with event Id: "+ eventId);
			return;
		}
		
		notificationService.sendOrderErrorEmailNotification(orderErrorEvent);
		
		OrderEventsEntity orderEventsEntity = new OrderEventsEntity(eventId);
		orderEventsRepository.save(orderEventsEntity);
	}
}
