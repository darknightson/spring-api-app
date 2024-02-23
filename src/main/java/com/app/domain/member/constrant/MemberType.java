package com.app.domain.member.constrant;

import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;

import java.util.Arrays;

public enum MemberType {
    KAKAO, GOOGLE, NAVER, APPLE;

    public static MemberType from(String memberType) {
        return MemberType.valueOf(memberType.toUpperCase());
    }

    public static boolean isMemberType(String memberType) {
        return Arrays.stream(MemberType.values())
                .anyMatch(type -> type.name().equals(memberType));
    }
}
