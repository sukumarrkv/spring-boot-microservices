package com.sukumar.bookstore.orders.web.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.sukumar.bookstore.orders.AbstractIntegrationTest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.testdata.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class OrderControllerIntegrationTest extends AbstractIntegrationTest{

	@Nested //New feature in junit 5 where we can create nested test classes
	class creatOrderTests {
		
		@Test
		void shouldCreateOrderSuccessfully() {
			String payload = """
				{
				    "customer": {
				        "name": "test",
				        "emailAddress": "test@test.com",
				        "phoneNumber": "7578676"
				    },
				    "deliveryAddress": {
				        "addressLine1": "test1",
				        "addressLine2": "test2",
				        "city": "test city",
				        "state": "test state",
				        "zipCode": "24234",
				        "country": "India"
				    },
				    "items": [
				        {
				            "code": "P100",
				            "name": "test",
				            "price": "23.33",
				            "quantity": "1"
				        }
				    ]
				}
					""";
			//CreateOrderRequest orderRequest = TestDataFactory.createValidOrderRequest();
			RestAssured.given().contentType(ContentType.JSON).body(payload)
			.log().all().post("/api/order/").then().log().all()
			.statusCode(HttpStatus.CREATED.value())
			.body("orderNumber", Matchers.notNullValue());
		}
		
		@Test
		void shouldReturnBadRequestWhenMadatoryDataIsMissing() {
			CreateOrderRequest orderRequest = TestDataFactory.createOrderRequestWithInvalidCustomer();
			RestAssured.given().contentType(ContentType.JSON).body(orderRequest)
			.post("/api/order/").then().statusCode(HttpStatus.BAD_REQUEST.value());
		}
	}
}
