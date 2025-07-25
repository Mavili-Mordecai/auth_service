package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.UnauthorizedException;
import com.locus_narrative.auth_service.domain.exceptions.UserNotFoundException;
import com.locus_narrative.auth_service.domain.ports.UserPort;
import com.locus_narrative.auth_service.domain.services.PasswordService;

public class SignInUseCase {
    private final UserPort port;
    private final PasswordService passwordService;

    public SignInUseCase(UserPort port, PasswordService passwordService) {
        this.port = port;
        this.passwordService = passwordService;
    }

    public UserEntity invoke(String login, String password) throws UserNotFoundException, UnauthorizedException {
        UserEntity entity = port.getByLogin(login);

        if (!passwordService.matches(password, entity.getPassword()))
            throw new UnauthorizedException("The credentials are incorrect.");

        return entity;
    }
}
