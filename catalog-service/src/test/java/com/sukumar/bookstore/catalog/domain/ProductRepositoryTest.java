package com.sukumar.bookstore.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest 
//This is also called slice annotation where we testing a slice of application (repository layer)
//When we use DataJpaTest annotation spring framework will use in memory database to run these tests.
//It will look into for any in memory database present in class path. If it not present it will throw error.
@Sql("/jpa-test-data.sql")
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void shouldGetAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		assertEquals(15, products.size());
	}
	
	@Test
	void shouldGetProductByCode() {
		ProductEntity productEntity = productRepository.findByCode("P100").orElseThrow();
		assertThat(productEntity.getCode()).isEqualTo("P100");
		assertThat(productEntity.getName()).isEqualTo("The Hunger Games");
		assertThat(productEntity.getDescription()).isEqualTo("Winning will make you famous. Losing means certain death...");
		assertThat(productEntity.getImageUrl()).isEqualTo("https://images.gr-assets.com/books/1447303603l/2767052.jpg");
		assertThat(productEntity.getPrice()).isEqualTo(new BigDecimal("34.00"));
	}
	
	@Test
	void shouldGetProductByCodeReturnEmpty() {
		assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
	}
}
