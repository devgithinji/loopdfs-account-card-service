package org.loopdfs.accountcardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAuditImpl")
public class AccountCardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountCardServiceApplication.class, args);
    }

}
