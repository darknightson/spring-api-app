package com.app.api.login.service;

import com.app.api.login.dto.OAuthLoginDto;
import com.app.domain.member.constrant.Role;
import com.app.domain.member.entity.MemberEntity;
import com.app.domain.member.service.MemberService;
import com.app.external.oauth.model.OAuthAttributes;
import com.app.external.oauth.service.SocialLoginApiService;
import com.app.external.oauth.service.SocialLoginApiServiceFactory;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthLoginService {

    private final MemberService memberService;
    private final JwtTokenManager jwtTokenManager;

    public OAuthLoginDto.Response oauthLogin(String accessToken, OAuthLoginDto.Request request) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory
                .getSocialLoginApiService(request.getMemberType());

        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        Optional<MemberEntity> memberEntity = memberService.findMemberByEmail(userInfo.getEmail());
        JwtTokenDto token;
        // 신규 회원일 경우
        if  ( !memberEntity.isPresent() ){
            MemberEntity saveMember = userInfo.toMemberEntity(request.getMemberType(), Role.ADMIN);
            MemberEntity member = memberService.registerMember(saveMember);
            // 토큰 생성
            token = jwtTokenManager.createToken(member.getId(), saveMember.getRole());
            // refresh token create
            member.updateRefreshToken(token);
        }

        // 기존 회원일 경우
        else {
            MemberEntity member = memberEntity.get();
            // 토큰 생성
            token = jwtTokenManager.createToken(member.getId(), member.getRole());
            member.updateRefreshToken(token);
        }
        return OAuthLoginDto.Response.of(token);
    }
}
