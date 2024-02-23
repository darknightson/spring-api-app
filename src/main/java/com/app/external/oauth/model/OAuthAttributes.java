package com.app.external.oauth.model;

import com.app.domain.member.constrant.MemberType;
import com.app.domain.member.constrant.Role;
import com.app.domain.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;
    private MemberType memberType;

    public MemberEntity toMemberEntity(MemberType memberType, Role role) {
        return MemberEntity.builder()
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .role(role)
                .build();
    }
}
