package com.locus_narrative.auth_service.infrastructure.config.usecases;

import com.locus_narrative.auth_service.application.usecases.DeleteUserUseCase;
import com.locus_narrative.auth_service.application.usecases.GetUserByUuidUseCase;
import com.locus_narrative.auth_service.application.usecases.SignInUseCase;
import com.locus_narrative.auth_service.application.usecases.SignUpUseCase;
import com.locus_narrative.auth_service.domain.ports.UserPort;
import com.locus_narrative.auth_service.domain.services.PasswordPolicyService;
import com.locus_narrative.auth_service.domain.services.PasswordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCasesConfig {
    @Bean
    public GetUserByUuidUseCase getUserByUuidUseCase(UserPort port) {
        return new GetUserByUuidUseCase(port);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserPort port, GetUserByUuidUseCase getUserByUuidUseCase) {
        return new DeleteUserUseCase(port, getUserByUuidUseCase);
    }

    @Bean
    public SignInUseCase signInUseCase(UserPort port, PasswordService passwordService) {
        return new SignInUseCase(port, passwordService);
    }

    @Bean
    public SignUpUseCase signUpUseCase(UserPort port, PasswordService passwordService, PasswordPolicyService passwordPolicyService) {
        return new SignUpUseCase(port, passwordService, passwordPolicyService);
    }
}
