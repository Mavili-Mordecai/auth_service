package com.locus_narrative.auth_service.infrastructure.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService implements com.locus_narrative.auth_service.domain.services.PasswordService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(CharSequence raw) {
        return passwordEncoder.encode(raw);
    }

    @Override
    public boolean matches(CharSequence raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }
}
