package com.app.domain.member.service;

import com.app.domain.member.entity.MemberEntity;
import com.app.domain.member.repository.MemberRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberEntity registerMember(MemberEntity memberEntity) {
        this.validateDuplicateMember(memberEntity.getEmail());
        return memberRepository.save(memberEntity);
    }

    private void validateDuplicateMember(String email) {
        memberRepository.findByEmail(email).ifPresent(m -> {
                    throw new MemberException(ErrorCode.DUPLICATE_MEMBER_EMAIL);
                });
    }

    public Optional<MemberEntity> findMemberByEmail(String email) {
        Optional<MemberEntity> byEmail = memberRepository.findByEmail(email);
        return byEmail;
    }

    public MemberEntity findMemberByRefreshToken(String refreshToken) {
        MemberEntity memberEntity = memberRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new AuthenticationException(ErrorCode.INVALID_REFRESH_TOKEN));

        // Refresh Token 만료 시간 체크
        LocalDateTime tokenExpirationTime = memberEntity.getTokenExpirationTime();

        if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        return memberEntity;
    }
}
