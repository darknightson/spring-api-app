package com.app.api.logout.controller;

import com.app.api.login.validator.OAuthValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogoutController {

    private final OAuthValidator oAuthValidator;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");
        // 검증
        oAuthValidator.validateAuthorization(authorization);


        return ResponseEntity.ok().body("logout success");
    }
}
