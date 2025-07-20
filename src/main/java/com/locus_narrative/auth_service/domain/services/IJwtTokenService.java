package com.locus_narrative.auth_service.domain.services;

import java.util.Map;

public interface IJwtTokenService {
    String generateAccessToken(String subject, Map<String, Object> claims);
    String generateRefreshToken(String subject);
    Object validateAccessToken(String token);
    Object validateRefreshToken(String token);
}
