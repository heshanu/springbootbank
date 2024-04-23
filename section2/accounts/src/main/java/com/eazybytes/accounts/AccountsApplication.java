package com.eazybytes.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@ComponentScans({ @ComponentScan("com.eazybytes.accounts.controller") })
//@EnableJpaRepositories("com.eazybytes.accounts.repository")
//@EntityScan("com.eazybytes.accounts.service")

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Account MicroService REST API DOC",
				description = "DOCs for API",
				version = "v1",
				contact = @Contact(
						name="Heshan Umayanga",
						email="h@gmail.com",
						url="test.com"
				),
				license = @License(
						name ="Apache 2.o",
						url = ""
				)
		),
		externalDocs = @ExternalDocumentation(
			description = "Eaxy bank REST API DOC",
			url = "test.com"
		)

)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
