package com.sukumar.bookstore.orders;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import dasniko.testcontainers.keycloak.KeycloakContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	private static final String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.0";
	private static final String REALM_IMPORT_FILE_PATH = "";
	private static final String REALM_NAME = "bookstore";
	@Bean
	@ServiceConnection
	MySQLContainer<?> mysqlContainer() {
		return new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
	}

	@Bean
	@ServiceConnection
	RabbitMQContainer rabbitContainer() {
		return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13.7-management"));
	}

	@Bean
	KeycloakContainer keycloakContainer(DynamicPropertyRegistry registry) {
		KeycloakContainer keycloakContainer = new KeycloakContainer(KEYCLOAK_IMAGE).withRealmImportFile(REALM_IMPORT_FILE_PATH);
		registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloakContainer.getAuthServerUrl()+"/realms/"+REALM_NAME);
		return keycloakContainer;
		
	}
}
