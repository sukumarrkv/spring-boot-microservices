package com.sukumar.bookstore.orders.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sukumar.bookstore.orders.domain.OrderService;
import com.sukumar.bookstore.orders.domain.SecurityService;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.testdata.TestDataFactory;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest {

	@MockitoBean
	private OrderService orderService;
	
	@MockitoBean
	private SecurityService securityService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		BDDMockito.when(securityService.getLoginUserName()).thenReturn("User");
		//BDDMockito.given(securityService.getLoginUserName()).willReturn("User");
	}
	
	@Test
	void shouldReturnBadRequestWhenOrderPayloadIsInvalid() throws Exception {
		CreateOrderRequest request = TestDataFactory.createOrderRequestWithInvalidCustomer();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order/").contentType(MediaType.APPLICATION_JSON)
			   .content(objectMapper.writeValueAsString(request)))
		       .andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
