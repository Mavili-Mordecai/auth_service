package com.locus_narrative.auth_service.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${auth.token.access.publicKey}")
    private String publicKeyStr;

    @Bean
    public PublicKey jwtPublicKey() {
        try {
            String publicKeyPEM = publicKeyStr
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse public key", e);
        }
    }
}