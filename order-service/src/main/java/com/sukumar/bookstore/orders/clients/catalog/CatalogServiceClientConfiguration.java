package com.sukumar.bookstore.orders.clients.catalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.sukumar.bookstore.orders.domain.ApplicationProperties;

@Configuration
public class CatalogServiceClientConfiguration {
	
	@Bean
	RestClient restClient(ApplicationProperties properties) {
		return RestClient.builder().baseUrl(properties.catalogServiceUrl()).build();
	}
}
