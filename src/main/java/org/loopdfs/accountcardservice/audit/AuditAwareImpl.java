package org.loopdfs.accountcardservice.audit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAuditImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String APP_NAME;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(APP_NAME);
    }
}
