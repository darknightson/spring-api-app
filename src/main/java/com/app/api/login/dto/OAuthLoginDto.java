package com.app.api.login.dto;

import com.app.domain.member.constrant.MemberType;
import com.app.global.jwt.dto.JwtTokenDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class OAuthLoginDto {

    @Getter @Setter
    public static class Request {
        private MemberType memberType;
    }

    @Getter @Setter @Builder
    public static class Response {
        private String grantType;

        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpirationTime;

        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpirationTime;

        public static Response of(JwtTokenDto jwtTokenDto) {
            return Response.builder()
                    .grantType(jwtTokenDto.getGrantType())
                    .accessToken(jwtTokenDto.getAccessToken())
                    .accessTokenExpirationTime(jwtTokenDto.getAccessTokenExpirationTime())
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .refreshTokenExpirationTime(jwtTokenDto.getRefreshTokenExpirationTime())
                    .build();
        }
    }
}
