package com.app.api.token.service;

import com.app.api.token.dto.AccessTokenResponseDto;
import com.app.domain.member.entity.MemberEntity;
import com.app.domain.member.service.MemberService;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.service.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final MemberService memberService;
    private final JwtTokenManager jwtTokenManager;

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
        MemberEntity memberEntity = memberService.findMemberByRefreshToken(refreshToken);

        Date accessTokenExpirationTime = jwtTokenManager.createAccessTokenExpirationTime();
        String accessToken = jwtTokenManager.createAccessToken(memberEntity.getId(), memberEntity.getRole(), accessTokenExpirationTime);

        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpirationTime(accessTokenExpirationTime)
                .build();
    }
}
