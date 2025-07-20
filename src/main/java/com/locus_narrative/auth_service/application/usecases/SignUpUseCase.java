package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.Either;
import com.locus_narrative.auth_service.domain.entities.EUserSignUpError;
import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.ports.IUserPort;
import com.locus_narrative.auth_service.domain.services.IPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SignUpUseCase {
    private final IUserPort port;
    private final IPasswordService passwordService;

    @Value("${password.requirements.min-length}")
    private Integer passwordMinLength;

    public Either<EUserSignUpError, UserEntity> invoke(String login, String password) {
        if (password.length() < passwordMinLength) return Either.left(EUserSignUpError.WEAK_PASSWORD);

        return port.insert(new UserEntity(UUID.randomUUID(), login, passwordService.encode(password)));
    }
}
