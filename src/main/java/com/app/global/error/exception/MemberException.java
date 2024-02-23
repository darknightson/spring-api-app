package com.app.global.error.exception;

import com.app.global.error.ErrorCode;

public class MemberException extends BusinessException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
