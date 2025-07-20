package com.locus_narrative.auth_service.presentation.controllers;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.Responses;
import com.locus_narrative.auth_service.application.dto.requests.RefreshRequest;
import com.locus_narrative.auth_service.application.dto.requests.UserRequest;
import com.locus_narrative.auth_service.application.dto.responses.TokenResponse;
import com.locus_narrative.auth_service.application.usecases.SignInUseCase;
import com.locus_narrative.auth_service.application.usecases.SignUpUseCase;
import com.locus_narrative.auth_service.domain.Either;
import com.locus_narrative.auth_service.domain.entities.EUserSignUpError;
import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.services.IJwtTokenService;
import io.jsonwebtoken.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Методы входа и регистрации")
public class AuthController {
    private final IJwtTokenService _jwtTokenService;
    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;

    @Value("${password.requirements.min-length}")
    private String passwordMinLength;

    @PostMapping("/sign-in")
    private ResponseEntity<IResponse<?>> signIn(@RequestBody @Validated(UserRequest.class) UserRequest request) {
        UserEntity user = signInUseCase.invoke(request.getLogin(), request.getPassword());

        if (user.getId() == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Responses.unauthorized("Invalid credentials."));

        return generateTokens(user.getUuid());
    }

    @PostMapping("/sign-up")
    private ResponseEntity<IResponse<?>> signUp(@RequestBody @Validated(UserRequest.class) UserRequest request) {
        Either<EUserSignUpError, UserEntity> userOrError = signUpUseCase.invoke(request.getLogin(), request.getPassword());

        if (userOrError.isLeft())
            return switch (userOrError.getLeft()) {
                case LOGIN_BUSY -> ResponseEntity.status(HttpStatus.CONFLICT).body(
                        Responses.conflict("Login is busy.")
                );

                case WEAK_PASSWORD -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Responses.badRequest("Weak password. Minimum password length is " + passwordMinLength + ".")
                );
            };

        return generateTokens(userOrError.getRight().getUuid());
    }

    @PostMapping("/refresh")
    private ResponseEntity<IResponse<?>> refresh(@RequestBody @Validated(RefreshRequest.class) RefreshRequest request) {
        String message = "Internal error";

        try {
            Object claimsObj = _jwtTokenService.validateRefreshToken(request.getRefresh());
            if (claimsObj instanceof Claims claims) return generateTokens(UUID.fromString(claims.getSubject()));
        } catch (ExpiredJwtException expEx) {
            message = "Token expired.";
        } catch (UnsupportedJwtException unsEx) {
            message = "Unsupported jwt.";
        } catch (MalformedJwtException mjEx) {
            message = "Malformed jwt.";
        } catch (SignatureException sEx) {
            message = "Invalid signature.";
        } catch (Exception e) {
            message = "Invalid token.";
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Responses.unauthorized(message));
    }

    private ResponseEntity<IResponse<?>> generateTokens(UUID uuid) {
        String accessToken = _jwtTokenService.generateAccessToken(uuid.toString(), new HashMap<>());
        String refreshToken = _jwtTokenService.generateRefreshToken(uuid.toString());
        return ResponseEntity.ok(Responses.ok(new TokenResponse(accessToken, refreshToken)));
    }
}
