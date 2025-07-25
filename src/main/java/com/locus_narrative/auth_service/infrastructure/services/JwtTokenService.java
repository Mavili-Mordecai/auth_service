package com.locus_narrative.auth_service.infrastructure.services;

import com.locus_narrative.auth_service.presentation.config.AuthConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Service
public class JwtTokenService implements com.locus_narrative.auth_service.domain.services.JwtTokenService {
    private AuthConfig _authConfig;

    @Override
    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .claim("role", "user")
                .expiration(new Date(System.currentTimeMillis() + _authConfig.getAccess().getExpiry() * 1000))
                .signWith(_authConfig.getAccess().getPrivateKeyObject(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .claim("role", "user")
                .expiration(new Date(System.currentTimeMillis() + _authConfig.getRefresh().getExpiry() * 1000))
                .signWith(_authConfig.getRefresh().getPrivateKeyObject(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public Object validateAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(_authConfig.getAccess().getPublicKeyObject())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Object validateRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(_authConfig.getRefresh().getPublicKeyObject())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
