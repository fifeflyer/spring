package com.example.account.audit;

import com.example.account.context.UserContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorProvider implements AuditorAware<String> {

    private final UserContext userContext;

    public AuditorProvider(UserContext userContext) {
        this.userContext = userContext;
    }

    public String getCurrentAuditor() {
        return userContext.getUser();
    }
}
