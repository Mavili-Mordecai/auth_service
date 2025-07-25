package com.locus_narrative.auth_service.domain.services;

import com.locus_narrative.auth_service.domain.exceptions.WeakPasswordException;

public class WeakPasswordPolicyService implements PasswordPolicyService {
    private final Integer passwordMinLength;

    public WeakPasswordPolicyService(Integer passwordMinLength) {
        this.passwordMinLength = passwordMinLength;
    }

    @Override
    public void validate(String password) throws WeakPasswordException {
        if (password.length() < passwordMinLength)
            throw new WeakPasswordException("Password is weak");
    }
}
