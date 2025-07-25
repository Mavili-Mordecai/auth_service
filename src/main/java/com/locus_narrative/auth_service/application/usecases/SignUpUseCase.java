package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.LoginIsBusyException;
import com.locus_narrative.auth_service.domain.exceptions.WeakPasswordException;
import com.locus_narrative.auth_service.domain.gateways.UserPort;
import com.locus_narrative.auth_service.domain.services.IPasswordService;

import java.util.UUID;

public class SignUpUseCase {
    private final UserPort port;
    private final IPasswordService passwordService;
    private final Integer passwordMinLength;

    public SignUpUseCase(UserPort port, IPasswordService passwordService, Integer passwordMinLength) {
        this.port = port;
        this.passwordService = passwordService;
        this.passwordMinLength = passwordMinLength;
    }

    public UserEntity invoke(String login, String password) throws WeakPasswordException, LoginIsBusyException {
        if (password.length() < passwordMinLength)
            throw new WeakPasswordException("Password is weak");

        return port.insert(new UserEntity(UUID.randomUUID(), login, passwordService.encode(password)));
    }
}
