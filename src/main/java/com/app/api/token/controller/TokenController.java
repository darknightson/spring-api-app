package com.app.api.token.controller;

import com.app.api.login.validator.OAuthValidator;
import com.app.api.token.dto.AccessTokenResponseDto;
import com.app.api.token.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;
    private final OAuthValidator oAuthValidator;

    @PostMapping("/access-token/issue")
    public ResponseEntity<AccessTokenResponseDto> createAccessToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        // 헤더 검증
        oAuthValidator.validateAuthorization(authorizationHeader);

        AccessTokenResponseDto accessTokenByRefreshToken = tokenService.createAccessTokenByRefreshToken(authorizationHeader);

        return ResponseEntity.ok().body(accessTokenByRefreshToken);
    }
}
