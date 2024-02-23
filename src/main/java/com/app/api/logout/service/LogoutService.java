package com.app.api.logout.service;

import com.app.domain.member.service.MemberService;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.service.JwtTokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogoutService {

    private final MemberService memberService;
    private final JwtTokenManager jwtTokenManager;

    @Transactional
    public void logout(String accessToken) {

        // STEP.1 : 토큰 검증
        jwtTokenManager.validateToken(accessToken);

        // STEP.2 : 토큰 타입 확인 (accessToken 인지 refreshToken 인지)
        Claims tokenClaims = jwtTokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

    }
}
