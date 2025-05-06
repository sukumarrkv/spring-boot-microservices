package com.sukumar.bookstore.orders.web.controllers;

import org.springframework.stereotype.Service;

@Service
public class RabbitListener {

	//@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${orders.new-orders-queue}")
	public void newOrdersListener(String message) {
		System.out.println("Message from new order queue is : " + message);
	}
	
	//@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${orders.delivered-orders-queue}")
	public void deliverdOrdersListener(String message) {
		System.out.println("Message from delivered order queue is : " + message);
	}
}
