package com.app.external.oauth.service;

import com.app.domain.member.constrant.MemberType;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialLoginApiService;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiService) {
        this.socialLoginApiService = socialLoginApiService;
    }

    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType) {
        String socialLoginBeanName = "";
        if (memberType == MemberType.KAKAO) {
            socialLoginBeanName = "kakaoLoginApiServiceImpl";
        }
        return socialLoginApiService.get(socialLoginBeanName);
    }
}
