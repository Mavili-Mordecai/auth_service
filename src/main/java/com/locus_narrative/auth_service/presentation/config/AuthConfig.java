package com.locus_narrative.auth_service.presentation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

@Component
@ConfigurationProperties(prefix = "auth.token")
@Getter
@Setter
public class AuthConfig {
    private TokenConfig access;
    private TokenConfig refresh;

    @Getter
    @Setter
    public static class TokenConfig {
        private Long expiry;
        private Boolean attachToResponse;
        private String publicKey;
        private String privateKey;

        public PublicKey getPublicKeyObject() {
            return KeyUtils.parsePublicKey(publicKey);
        }

        public PrivateKey getPrivateKeyObject() {
            return KeyUtils.parsePrivateKey(privateKey);
        }
    }
}
