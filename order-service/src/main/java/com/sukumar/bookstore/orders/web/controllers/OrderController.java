package com.sukumar.bookstore.orders.web.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sukumar.bookstore.orders.domain.OrderServcie;
import com.sukumar.bookstore.orders.domain.SecurityService;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

	private OrderServcie orderServcie;
	private SecurityService securityService;
	
	public OrderController(OrderServcie orderServcie, SecurityService securityService) {
		super();
		this.orderServcie = orderServcie;
		this.securityService = securityService;
	}
	
	@PostMapping
	public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
		String userName = securityService.getLoginUserName();
		return orderServcie.createOrder(createOrderRequest, userName);
	}
}
