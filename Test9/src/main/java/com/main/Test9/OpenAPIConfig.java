package com.main.Test9;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
// http://localhost:8084/swagger-ui/index.html#/
@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Test 9 API.",
				version = "v1",
				description = "API documentation for Test 9 Project.",
				contact = @Contact(
						name = "Arjun Dhatbale",
						email = "arjundhatbale321@gmail.com",
						url = "http://localhost:8084/swagger-ui/index.html#/"
						)
				)
		)
public class OpenAPIConfig {

}
