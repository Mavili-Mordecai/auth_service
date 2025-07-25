package com.locus_narrative.auth_service.domain.services;

import com.locus_narrative.auth_service.domain.exceptions.WeakPasswordException;

public interface PasswordPolicyService {
    void validate(String password) throws WeakPasswordException;
}
