package com.sukumar.bookstore.orders.web.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.sukumar.bookstore.orders.AbstractIntegrationTest;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.OrderStatus;
import com.sukumar.bookstore.orders.domain.models.OrderSummary;
import com.sukumar.bookstore.orders.testdata.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

@Sql("/test-data.sql")
public class OrderControllerIntegrationTest extends AbstractIntegrationTest{

	@Nested //New feature in junit 5 where we can create nested test classes
	class creatOrderTests {
		
		@Test
		void shouldCreateOrderSuccessfully() {
			mockGetProductByCode("P100", "test", new BigDecimal(23.33));
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
			RestAssured.given().contentType(ContentType.JSON).header("Authorization", "Bearer", getToken())
			.body(payload).log().all().post("/api/order/").then().log().all()
			.statusCode(HttpStatus.CREATED.value())
			.body("orderNumber", Matchers.notNullValue());
		}
		
		@Test
		void shouldReturnBadRequestWhenMadatoryDataIsMissing() {
			CreateOrderRequest orderRequest = TestDataFactory.createOrderRequestWithInvalidCustomer();
			RestAssured.given().contentType(ContentType.JSON).body(orderRequest)
			.post("/api/order/").then().statusCode(HttpStatus.BAD_REQUEST.value())
			.body("errors", Matchers.contains("Customer phone number is required"));
		}
	}
	
	@Nested
	class GetOrderTests {
		
		//@Test
		void shouldGetAllOrders() {
			List<OrderSummary> orders = RestAssured.given().when().get("/api/order/").then().statusCode(HttpStatus.OK.value())
			           .extract().body().as(new TypeRef<>() {});
			
			assertEquals(2, orders.size());
		}
		
		//@Test
		void shouldGetOrderByOrderNumberTest() {
			String orderNumber ="order-456";
			RestAssured.given().when().get("/api/order/{orderNumber}", orderNumber).then().statusCode(HttpStatus.OK.value())
			           .body("orderNumber", Matchers.is(orderNumber))
			           .body("items.size()", Matchers.is(1));
		}
	}
}
