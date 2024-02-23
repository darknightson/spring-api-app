package com.app.external.oauth.kakao.service;

import com.app.domain.member.constrant.MemberType;
import com.app.external.oauth.kakao.client.KakaoUserInfoClient;
import com.app.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import com.app.external.oauth.model.OAuthAttributes;
import com.app.external.oauth.service.SocialLoginApiService;
import com.app.global.jwt.constant.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final String CONTENT_TYPE = "application/x-www-from-urlencoded;charset=utf-8";
    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        log.info("KakaoLoginApiServiceImpl getUserInfo accessToken : {}", accessToken);
        KakaoUserInfoResponseDto kaKaoUserInfo = kakaoUserInfoClient.getKaKaoUserInfo(CONTENT_TYPE, accessToken);

        KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = kaKaoUserInfo.getKakaoAccount();

        return OAuthAttributes.builder()
                .name(kakaoAccount.getProfile().getNickname())
                .email(!StringUtils.hasText(kakaoAccount.getEmail()) ? kaKaoUserInfo.getId()
                        : kakaoAccount.getProfile().getNickname())
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .memberType(MemberType.KAKAO)
                .build();
    }
}
