package com.locus_narrative.auth_service.domain.gateways;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.LoginIsBusyException;
import com.locus_narrative.auth_service.domain.exceptions.UserNotFoundException;

import java.util.UUID;

public interface UserPort {
    UserEntity getByLogin(String login) throws UserNotFoundException;
    UserEntity getByUuid(UUID uuid) throws UserNotFoundException;

    UserEntity insert(UserEntity entity) throws LoginIsBusyException;
    UserEntity update(UserEntity entity) throws UserNotFoundException;

    boolean deleteByUuid(UUID uuid);
}
