package com.sukumar.bookstore.catalog.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import com.sukumar.bookstore.catalog.AbstractIntegrationTest;
import com.sukumar.bookstore.catalog.domain.Product;

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
	
	@Test
	void shouldGetProductByCode() {
		Product product = RestAssured.given().contentType(ContentType.JSON).when()
		           .get("/api/products/{code}", "P100")
		           .then()
		           .statusCode(200)
		           .assertThat()
		           .extract()
		           .body()
		           .as(Product.class);
		
		assertThat(product.code()).isEqualTo("P100");
		assertThat(product.name()).isEqualTo("The Hunger Games");
		assertThat(product.description()).isEqualTo("Winning will make you famous. Losing means certain death...");
		assertThat(product.imageUrl()).isEqualTo("https://images.gr-assets.com/books/1447303603l/2767052.jpg");
		assertThat(product.price()).isEqualTo(new BigDecimal("34.00"));
	}
	
	@Test
	void shouldReturnNotFoundWhenProductNotFound() {
		String code = "invalid_product_code";
		RestAssured.given().contentType(ContentType.JSON).when()
		           .get("/api/products/{code}", code)
		           .then()
		           .statusCode(404)
		           .body("status", Matchers.is(404))
		           .body("title", Matchers.is("Product Not Found"))
		           .body("detail", Matchers.is("Product with code "+ code + " not found"));
	}
}
