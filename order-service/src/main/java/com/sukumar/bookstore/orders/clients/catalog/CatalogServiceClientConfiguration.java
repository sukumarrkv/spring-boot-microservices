package com.sukumar.bookstore.orders.clients.catalog;

import java.time.Duration;

import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import com.sukumar.bookstore.orders.domain.ApplicationProperties;

@Configuration
public class CatalogServiceClientConfiguration {
	
	@Bean
	RestClient restClient(ApplicationProperties properties) {
		//Time limiter on Rest client itself instead of using resilience4j as it using  Completable future
		//and it is complex.
		ClientHttpRequestFactory clientHttpRequestFactory = ClientHttpRequestFactoryBuilder.simple()
				.withCustomizer((customizer) -> {
					customizer.setConnectTimeout(Duration.ofSeconds(5));
					customizer.setReadTimeout(Duration.ofSeconds(5));
				}).build();
		
		return RestClient.builder().baseUrl(properties.catalogServiceUrl())
				.requestFactory(clientHttpRequestFactory)
				.build();
	}
}
