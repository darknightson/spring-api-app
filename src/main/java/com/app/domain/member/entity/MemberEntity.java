package com.app.domain.member.entity;

import com.app.domain.common.BaseEntity;
import com.app.domain.member.constrant.MemberType;
import com.app.domain.member.constrant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.util.DateTimeUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    // 쇼설 로그인은 저장을 하지 않고 차후 폼 로그인시 저장할 예정이라 nullable = true
    @Column(length = 200)
    private String password;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 200)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;


    @Builder
    public MemberEntity(MemberType memberType, String email, String password, String memberName, String profile, Role role) {
        this.memberType = memberType;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.profile = profile;
        this.role = role;
    }

    public void updateRefreshToken(JwtTokenDto token) {
        this.refreshToken = token.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(token.getRefreshTokenExpirationTime());
    }
}
