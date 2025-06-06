package com.sukumar.bookstore.gateway.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.utils.Constants;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class SwaggerConfiguration {

	private RouteDefinitionLocator locator;
	private SwaggerUiConfigProperties swaggerUiConfigProperties;

	public SwaggerConfiguration(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
		super();
		this.locator = locator;
		this.swaggerUiConfigProperties = swaggerUiConfigProperties;
	}
	
	//This will dynamically generate swagger ui's for all the service added in api gateway.
	//This is alternative to defining swagger configuration for each service in application properties
	@PostConstruct
	public void init() {
		List<RouteDefinition> routeDefinitions = locator.getRouteDefinitions().collectList().block();
		Set<SwaggerUrl> urls = new HashSet<>();
		routeDefinitions.stream().filter(routeDefinition -> routeDefinition.getId().matches("*-service"))
		                .forEach(routeDefinition -> {
		                	String serviceName = routeDefinition.getId().replaceAll("-service", "");
		                	SwaggerUrl swaggerUrl = new SwaggerUrl(serviceName, Constants.DEFAULT_API_DOCS_URL + "/" +serviceName, null);
		                	urls.add(swaggerUrl);
		                });
		swaggerUiConfigProperties.setUrls(urls);
	}
	
}
