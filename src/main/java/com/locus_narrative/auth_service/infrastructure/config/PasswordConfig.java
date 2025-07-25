package com.locus_narrative.auth_service.infrastructure.config;

import com.locus_narrative.auth_service.domain.services.PasswordPolicyService;
import com.locus_narrative.auth_service.domain.services.WeakPasswordPolicyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordConfig {
    @Value("${password.requirements.min-length}")
    private Integer passwordMinLength;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordPolicyService passwordPolicyService() { return new WeakPasswordPolicyService(passwordMinLength); }
}
