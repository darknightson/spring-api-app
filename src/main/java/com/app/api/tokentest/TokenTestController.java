package com.app.api.tokentest;

import com.app.domain.member.constrant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.JwtTokenManager;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenTestController {

    private final JwtTokenManager jwtTokenManager;

    @GetMapping("/create")
    public JwtTokenDto createToken() {
        return jwtTokenManager.createToken(1l, Role.ADMIN);
    }

    @GetMapping("/valid")
    public String validateJwtToken(@RequestParam String token) {
        jwtTokenManager.validateToken(token);
        Claims tokenClaims = jwtTokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        Role role = Role.valueOf((String) tokenClaims.get("role"));

        return "memberId: " + memberId + ", role: " + role;
    }
}
