package com.locus_narrative.auth_service.presentation.controllers;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.Responses;
import com.locus_narrative.auth_service.application.dto.requests.RefreshRequest;
import com.locus_narrative.auth_service.application.dto.requests.UserRequest;
import com.locus_narrative.auth_service.application.dto.responses.TokenResponse;
import com.locus_narrative.auth_service.application.usecases.DeleteUserUseCase;
import com.locus_narrative.auth_service.application.usecases.GetUserByUuidUseCase;
import com.locus_narrative.auth_service.application.usecases.SignInUseCase;
import com.locus_narrative.auth_service.application.usecases.SignUpUseCase;
import com.locus_narrative.auth_service.domain.entities.UserEntity;
import com.locus_narrative.auth_service.domain.exceptions.LoginIsBusyException;
import com.locus_narrative.auth_service.domain.exceptions.UnauthorizedException;
import com.locus_narrative.auth_service.domain.exceptions.UserNotFoundException;
import com.locus_narrative.auth_service.domain.exceptions.WeakPasswordException;
import com.locus_narrative.auth_service.domain.services.IJwtTokenService;
import com.locus_narrative.auth_service.presentation.schemes.JwtTokensResponseScheme;
import io.jsonwebtoken.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Methods for user authentication.")
public class AuthController {
    private final IJwtTokenService _jwtTokenService;
    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserByUuidUseCase getUserByUuidUseCase;

    @Value("${password.requirements.min-length}")
    private String passwordMinLength;

    @Operation(
            summary = "Login to your account.",
            description = "Accepting login and password, it performs authentication and returns JWT tokens.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The credentials are correct.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokensResponseScheme.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "The credentials are incorrect.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/sign-in")
    private ResponseEntity<IResponse<?>> signIn(@RequestBody @Validated(UserRequest.class) UserRequest request) {
        try {
            UserEntity user = signInUseCase.invoke(request.getLogin(), request.getPassword());
            return generateTokens(user.getUuid());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responses.notFound(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Responses.unauthorized(e.getMessage()));
        }
    }

    @Operation(
            summary = "Register an account.",
            description = "Accepting login and password, creates an account and returns JWT tokens.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokensResponseScheme.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body or weak password.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "A user with this login already exists.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/sign-up")
    private ResponseEntity<IResponse<?>> signUp(@RequestBody @Validated(UserRequest.class) UserRequest request) {
        try {
            UserEntity userEntity = signUpUseCase.invoke(request.getLogin(), request.getPassword());
            return generateTokens(userEntity.getUuid());
        } catch (WeakPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Responses.badRequest(e.getMessage()));
        } catch (LoginIsBusyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Responses.conflict(e.getMessage()));
        }
    }

    @Operation(
            summary = "JWT token update.",
            description = "Accepting a refresh token returns new JWT tokens.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A new pair of JWT tokens.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokensResponseScheme.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid refresh token.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/refresh")
    private ResponseEntity<IResponse<?>> refresh(@RequestBody @Validated(RefreshRequest.class) RefreshRequest request) {
        String message = "Internal error";

        try {
            Object claimsObj = _jwtTokenService.validateRefreshToken(request.getRefresh());
            if (claimsObj instanceof Claims claims) {
                UUID uuid = UUID.fromString(claims.getSubject());

                getUserByUuidUseCase.invoke(uuid);

                return generateTokens(uuid);
            }
        } catch (ExpiredJwtException expEx) {
            message = "Token expired.";
        } catch (UnsupportedJwtException unsEx) {
            message = "Unsupported jwt.";
        } catch (MalformedJwtException mjEx) {
            message = "Malformed jwt.";
        } catch (SignatureException sEx) {
            message = "Invalid signature.";
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responses.notFound(e.getMessage()));
        } catch (Exception e) {
            message = "Invalid token.";
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Responses.unauthorized(message));
    }

    @Operation(
            summary = "Deletes a user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The user has been deleted.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid JWT-token.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = IResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/delete")
    private ResponseEntity<IResponse<?>> delete() {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID uuid;

        try {
            uuid = UUID.fromString(principal);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Responses.badRequest("The 'sub' field in the JWT token must contain a valid UUID."));
        }

        try {
            deleteUserUseCase.invoke(uuid);
            return ResponseEntity.ok(Responses.ok());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Responses.notFound(e.getMessage()));
        }
    }

    private ResponseEntity<IResponse<?>> generateTokens(UUID uuid) {
        String accessToken = _jwtTokenService.generateAccessToken(uuid.toString(), new HashMap<>());
        String refreshToken = _jwtTokenService.generateRefreshToken(uuid.toString());
        return ResponseEntity.ok(Responses.ok(new TokenResponse(accessToken, refreshToken)));
    }
}
