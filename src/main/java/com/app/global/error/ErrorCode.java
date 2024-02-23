package com.app.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "0001", "Invalid Input Value"),

    // 인증 에러
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료 되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A-003", "유효하지 않은 리프레시 토큰입니다."),

    INVALID_TOKEN_NULL(HttpStatus.UNAUTHORIZED, "A-003", "Authorization 헤더가 없습니다"),
    INVALID_TOKEN_BEARE(HttpStatus.UNAUTHORIZED, "A-004", "Bearer 토큰이 아닙니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A-005", "만료된 리프레시 토큰입니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-006", "지원하지 않는 토큰 타입 입니다."),
    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "지원하지 않는 회원 타입입니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.BAD_REQUEST, "M-002", "이미 존재하는 이메일입니다."),

    ;

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
