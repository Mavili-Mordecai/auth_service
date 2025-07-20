package com.locus_narrative.auth_service.domain.services;

public interface IPasswordService {
    String encode(CharSequence raw);
    boolean matches(CharSequence raw, String encoded);
}
