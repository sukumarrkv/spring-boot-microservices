package com.sukumar.bookstore.orders.web.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sukumar.bookstore.orders.domain.OrderService;
import com.sukumar.bookstore.orders.domain.SecurityService;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderResponse;
import com.sukumar.bookstore.orders.domain.models.OrderDTO;
import com.sukumar.bookstore.orders.domain.models.OrderSummary;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

	private OrderService orderService;
	private SecurityService securityService;
	
	public OrderController(OrderService orderService, SecurityService securityService) {
		super();
		this.orderService = orderService;
		this.securityService = securityService;
	}
	
	@PostMapping
	public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
		String userName = securityService.getLoginUserName();
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createOrderRequest, userName));
	}
	
	@GetMapping
	public ResponseEntity<List<OrderSummary>> getOrdersForUser() {
		String userName = securityService.getLoginUserName();
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderForUser(userName));
	}
	
	@GetMapping("/{orderNumber}")
	public ResponseEntity<OrderDTO> getOrderByOrderNumber(@PathVariable String orderNumber) {
		String userName = securityService.getLoginUserName();
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDetailsByOrderNumber(orderNumber, userName));
	}
}
