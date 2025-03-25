package com.sukumar.bookstore.catalog.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
