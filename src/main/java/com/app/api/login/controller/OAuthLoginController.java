package com.app.api.login.controller;

import com.app.api.login.dto.OAuthLoginDto;
import com.app.api.login.service.OAuthLoginService;
import com.app.api.login.validator.OAuthValidator;
import com.app.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthLoginController {

    private final OAuthValidator oAuthValidator;
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/login")
    public ResponseEntity<OAuthLoginDto.Response> oauthLogin(@RequestBody OAuthLoginDto.Request oAuthLoginDtoRequest,
                                                             HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        oAuthValidator.validateAuthorization(authorizationHeader);
        oAuthValidator.validatorMemberType(oAuthLoginDtoRequest.getMemberType());

        String accessToken = authorizationHeader.replace("Bearer ", "");
        log.info("accessToken : {}", accessToken);
        log.info("authorizationHeader : {}", authorizationHeader);

        OAuthLoginDto.Response response = oAuthLoginService.oauthLogin(authorizationHeader, oAuthLoginDtoRequest);
        return ResponseEntity.ok().body(response);
    }
}
