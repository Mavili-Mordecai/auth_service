package com.locus_narrative.auth_service.application.usecases;

import com.locus_narrative.auth_service.domain.ports.IUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final IUserPort port;

    public boolean invoke(UUID uuid) {
        return port.deleteByUuid(uuid);
    }
}
