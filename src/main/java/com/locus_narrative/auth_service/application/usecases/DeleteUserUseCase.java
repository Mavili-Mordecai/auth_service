package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.exceptions.UserNotFoundException;
import com.locus_narrative.auth_service.domain.ports.UserPort;

import java.util.UUID;

public class DeleteUserUseCase {
    private final UserPort port;
    private final GetUserByUuidUseCase getUserByUuidUseCase;

    public DeleteUserUseCase(UserPort port, GetUserByUuidUseCase getUserByUuidUseCase) {
        this.port = port;
        this.getUserByUuidUseCase = getUserByUuidUseCase;
    }

    public boolean invoke(UUID uuid) throws UserNotFoundException {
        getUserByUuidUseCase.invoke(uuid);
        return port.deleteByUuid(uuid);
    }
}
