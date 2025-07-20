package com.locus_narrative.auth_service.domain.ports;

import com.locus_narrative.auth_service.domain.Either;
import com.locus_narrative.auth_service.domain.entities.EUserSignUpError;
import com.locus_narrative.auth_service.domain.entities.UserEntity;

import java.util.UUID;

public interface IUserPort {
    UserEntity getByLogin(String login);
    UserEntity getByUuid(UUID uuid);

    Either<EUserSignUpError, UserEntity> insert(UserEntity entity);
    UserEntity update(UserEntity entity);

    boolean deleteByUuid(UUID uuid);
}
