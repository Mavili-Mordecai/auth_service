package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.ports.IUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserByUuidUseCase {
    private final IUserPort port;

    public UserEntity invoke(UUID userUuid) {
        return port.getByUuid(userUuid);
    }
}
