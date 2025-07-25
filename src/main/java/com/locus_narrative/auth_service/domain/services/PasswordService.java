package com.locus_narrative.auth_service.domain.services;

public interface PasswordService {
    String encode(CharSequence raw);
    boolean matches(CharSequence raw, String encoded);
}
