package com.sukumar.bookstore.catalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	//This below method was first written to allow executing API's from swagger UI (from browser). Without this
	//configuration it was throwing CORS issue. Since the request was made from browser there was CORS issue as
	//by default browser will allow to make request to one service (api gateway (localhost:8989))
	//to other different service (catalog service (localhost:8081)). But when we make request throw API gateway 
	//it worked because the request was made form api gateway and not from browser.
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
		        .allowedOriginPatterns("*")
		        .allowedHeaders("*")
		        .allowedMethods("*")
		        .allowCredentials(false);
	}
}
