package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.LoginIsBusyException;
import com.locus_narrative.auth_service.domain.exceptions.WeakPasswordException;
import com.locus_narrative.auth_service.domain.ports.UserPort;
import com.locus_narrative.auth_service.domain.services.PasswordService;
import com.locus_narrative.auth_service.domain.services.PasswordPolicyService;

import java.util.UUID;

public class SignUpUseCase {
    private final UserPort port;
    private final PasswordService passwordService;
    private final PasswordPolicyService passwordPolicyService;

    public SignUpUseCase(UserPort port, PasswordService passwordService, PasswordPolicyService passwordPolicyService) {
        this.port = port;
        this.passwordService = passwordService;
        this.passwordPolicyService = passwordPolicyService;
    }

    public UserEntity invoke(String login, String password) throws WeakPasswordException, LoginIsBusyException {
        passwordPolicyService.validate(password);

        return port.insert(new UserEntity(UUID.randomUUID(), login, passwordService.encode(password)));
    }
}
