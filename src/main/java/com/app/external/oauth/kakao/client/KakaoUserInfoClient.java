package com.app.external.oauth.kakao.client;

import com.app.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUserInfoClient", url = "https://kapi.kakao.com")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfoResponseDto getKaKaoUserInfo(@RequestHeader("Content-type") String contentType,
                                              @RequestHeader("Authorization") String accessToken);

}
