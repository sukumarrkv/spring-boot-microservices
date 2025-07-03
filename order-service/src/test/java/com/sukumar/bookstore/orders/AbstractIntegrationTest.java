package com.sukumar.bookstore.orders;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.OAuth2Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class AbstractIntegrationTest {

	private static final String CLIENT_ID = "bookstore-webapp";
	private static final String CLIENT_SECRET = "pnISniEewiC91pwrMDpf0ZufOoOvu0aS";
	private static final String USERNAME = "sukumar";
	private static final String PASSWORD = "sukumar1234";

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    
	@LocalServerPort
	private int port;
	
	//static WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}
	
//	@BeforeAll
//	static void beforeAll() {
//		wireMockContainer.start();
//		//WireMock.configureFor(wireMockContainer.getHost(), wireMockContainer.getPort());
//	}
	
//	@DynamicPropertySource
//	static void configureProperties(DynamicPropertyRegistry registry) {
//		registry.add("orders.catalog-service-url", wireMockContainer::getBaseUrl);
//	}
	
	protected void mockGetProductByCode(String code, String name, BigDecimal price) {
		WireMock.stubFor(WireMock.get(WireMock.urlMatching("/api/products/" + code))
				.willReturn(WireMock.aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withStatus(200).withBody(
								"""
								{
							        "code": "%s",
							        "name": "%s",
							        "price": "%f"
							    }
								""".formatted(code, name, price.doubleValue())
								)));
	}
	
	protected String getToken() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.put(OAuth2Constants.GRANT_TYPE, Collections.singletonList(OAuth2Constants.PASSWORD));
		map.put(OAuth2Constants.CLIENT_ID, Collections.singletonList(CLIENT_ID));
		map.put(OAuth2Constants.CLIENT_SECRET, Collections.singletonList(CLIENT_SECRET));
		map.put(OAuth2Constants.USERNAME, Collections.singletonList(USERNAME));
		map.put(OAuth2Constants.PASSWORD, Collections.singletonList(PASSWORD));
		
		String authServerUrl = oAuth2ResourceServerProperties.getJwt().getIssuerUri() + "/protocol/openid-connect/token";
		
		var request = new HttpEntity<>(map, httpHeaders);
		
		KeyCloakToken token = restTemplate.postForObject(authServerUrl, request, KeyCloakToken.class);
		assert token != null;
		return token.accessToken();
		
	}
	
	record KeyCloakToken(
			@JsonProperty("access_token")
			String accessToken
			) {}
}
