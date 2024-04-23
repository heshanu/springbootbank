package com.eazybyte.loan.loan;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loan MicroService REST API DOC",
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

public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
