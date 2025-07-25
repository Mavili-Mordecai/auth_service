package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.UserNotFoundException;
import com.locus_narrative.auth_service.domain.gateways.UserPort;

import java.util.UUID;

public class GetUserByUuidUseCase {
    private final UserPort port;

    public GetUserByUuidUseCase(UserPort port) {
        this.port = port;
    }

    public UserEntity invoke(UUID userUuid) throws UserNotFoundException {
        return port.getByUuid(userUuid);
    }
}
