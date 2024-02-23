package com.app.domain.member.repository;

import com.app.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByRefreshToken(String refreshToken);
}
