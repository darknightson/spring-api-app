package com.app.global.jwt.service;

import com.app.domain.member.constrant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenManager {

    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String secretKey;

    public JwtTokenDto createToken(Long memberId, Role role) {
        Date accessTokenExpirationTime = createAccessTokenExpirationTime();
        Date refreshTokenExpirationTime = refreshAccessTokenExpirationTime();
        String accessToken = createAccessToken(memberId, role, accessTokenExpirationTime);
        String refreshToken = refreshAccessToken(memberId, role, refreshTokenExpirationTime);
        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(accessTokenExpirationTime)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();

    }

    public Date createAccessTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime)); // 현재 시간으로 부터 15분이 지난 시간 반환
    }

    public Date refreshAccessTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long memberId, Role role, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name()) // 토큰 제목
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expirationTime) // 토큰 만료 시간
                .claim("memberId", memberId) // 토큰에 담을 정보
                .claim("role", role) // 토큰에 담을 정보
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(StandardCharsets.UTF_8)) // 토큰 암호화 알고리즘, secretKey
                .setHeaderParam("typ", "JWT") // 토큰 타입
                .compact();
    }

    public String refreshAccessToken(Long memberId, Role role, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name()) // 토큰 제목
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expirationTime) // 토큰 만료 시간
                .claim("memberId", memberId) // 토큰에 담을 정보
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(StandardCharsets.UTF_8)) // 토큰 암호화 알고리즘, secretKey
                .setHeaderParam("typ", "JWT") // 토큰 타입
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }
        return claims;
    }
}
