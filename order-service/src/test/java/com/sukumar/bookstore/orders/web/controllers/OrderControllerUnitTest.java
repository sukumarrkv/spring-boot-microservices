package com.sukumar.bookstore.orders.web.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.sukumar.bookstore.orders.domain.OrderService;
import com.sukumar.bookstore.orders.domain.SecurityService;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest {

	@MockitoBean
	private OrderService orderService;
	
	@MockitoBean
	private SecurityService securityService;
}
