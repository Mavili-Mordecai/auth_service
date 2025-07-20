package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.ports.IUserPort;
import com.locus_narrative.auth_service.domain.services.IPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInUseCase {
    private final IUserPort port;
    private final IPasswordService passwordService;

    public UserEntity invoke(String login, String password) {
        UserEntity entity = port.getByLogin(login);

        if (entity.getId() == null) return entity;

        return passwordService.matches(password, entity.getPassword())
                ? entity
                : new UserEntity();
    }
}
