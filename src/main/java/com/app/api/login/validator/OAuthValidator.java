package com.app.api.login.validator;

import com.app.domain.member.constrant.MemberType;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.MemberException;
import com.app.global.jwt.constant.GrantType;
import org.springframework.stereotype.Component;

@Component
public class OAuthValidator {

    public void validateAuthorization(String authorizationHeader) {

        // Header 값과 비교하여 Bearer Token 아닌 경우 예외 처리
        if (authorizationHeader == null ) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN_NULL);
        }
        if ( !authorizationHeader.startsWith(GrantType.BEARER.getType()+" ") ) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN_BEARE);
        }
    }

    public void validatorMemberType(MemberType memberType) {
        if (!MemberType.isMemberType(memberType.name())) {
            throw new MemberException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }
}

