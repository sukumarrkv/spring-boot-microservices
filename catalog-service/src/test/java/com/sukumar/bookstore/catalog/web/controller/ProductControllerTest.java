package com.sukumar.bookstore.catalog.web.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import com.sukumar.bookstore.catalog.AbstractIntegrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Sql("/test-data.sql")
public class ProductControllerTest extends AbstractIntegrationTest{

	@Test
	void shoudlReturnProducts() {
		RestAssured.given().contentType(ContentType.JSON).when().get("/api/products").then()
		           .statusCode(200)
		           .body("data", Matchers.hasSize(10))
		           .body("totalElements", Matchers.is(15))
		           .body("pageNumber", Matchers.is(1))
		           .body("totalPages", Matchers.is(2))
		           .body("isFirst", Matchers.is(true))
		           .body("isLast", Matchers.is(false))
		           .body("hasNext", Matchers.is(true))
		           .body("hasPrevious", Matchers.is(false));
	}
}
