package com.app.global.config;

import com.app.global.jwt.service.JwtTokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Value("${token.secret}")
    private String secretKey;

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationTime;

    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;

    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager(accessTokenExpirationTime, refreshTokenExpirationTime, secretKey);
    }
}
