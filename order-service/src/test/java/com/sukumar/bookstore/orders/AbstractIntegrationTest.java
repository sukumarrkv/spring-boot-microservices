package com.sukumar.bookstore.orders;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class AbstractIntegrationTest {

	@LocalServerPort
	private int port;
	
	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}
}
