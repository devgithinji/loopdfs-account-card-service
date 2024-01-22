package org.loopdfs.accountcardservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Loop DFS Account and Cards MicroService REST API Documentation",
                description = "REST API documentation explain the usage of Account and Card Microservice REST API endpoints",
                version = "v1",
                contact = @Contact(
                        name = "Dennis Githinji",
                        email = "wakahiad@gmail.com",
                        url = "https://dennisgithinji.tech"
                ),
                license = @License(
                        name = "Loop DFS 2.0",
                        url = "https://dennisgithinji.tech"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Loop DFS Account and Cards REST API Documentation",
                url = "https://dennisgithinji.tech"
        )
)
@RequiredArgsConstructor
public class AccountCardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountCardServiceApplication.class, args);
    }

}
