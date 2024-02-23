package com.app.web.kakaotoken.controller;

import com.app.web.kakaotoken.client.KakaoTokenClient;
import com.app.web.kakaotoken.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenClient kakaoTokenClient;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;


    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/oauth/kakao/callback")
    public @ResponseBody String loginCallback(String code) {
        String contentType = "application/x-www-form-urlencoded";
        KakaoTokenDto.Request request = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .code(code)
                .grant_type("authorization_code")
                .redirect_uri("http://localhost:8080/oauth/kakao/callback")
                .build();

        KakaoTokenDto.Response response = kakaoTokenClient.getToken(contentType, request);
        log.info("kakao token : {}", response);
        return "kakao token : " + response;
    }
}
