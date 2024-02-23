package com.app.global.error.exception;

import com.app.global.error.ErrorCode;

import java.io.BufferedInputStream;

public class AuthenticationException extends BusinessException {
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
