package com.locus_narrative.auth_service.infrastructure.config.usecases;

import com.locus_narrative.auth_service.application.usecases.DeleteUserUseCase;
import com.locus_narrative.auth_service.application.usecases.GetUserByUuidUseCase;
import com.locus_narrative.auth_service.application.usecases.SignInUseCase;
import com.locus_narrative.auth_service.application.usecases.SignUpUseCase;
import com.locus_narrative.auth_service.domain.gateways.UserPort;
import com.locus_narrative.auth_service.domain.services.IPasswordService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCasesConfig {
    @Value("${password.requirements.min-length}")
    private Integer passwordMinLength;

    @Bean
    public GetUserByUuidUseCase getUserByUuidUseCase(UserPort port) {
        return new GetUserByUuidUseCase(port);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserPort port, GetUserByUuidUseCase getUserByUuidUseCase) {
        return new DeleteUserUseCase(port, getUserByUuidUseCase);
    }

    @Bean
    public SignInUseCase signInUseCase(UserPort port, IPasswordService passwordService) {
        return new SignInUseCase(port, passwordService);
    }

    @Bean
    public SignUpUseCase signUpUseCase(UserPort port, IPasswordService passwordService) {
        return new SignUpUseCase(port, passwordService, passwordMinLength);
    }
}
