package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberNotFoundException(memberId)
                );
    }

    @Transactional
    public Member updateMember(Long memberId, Member updatedMember) {
        var member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(memberId)
        );

        member.setNickName(updatedMember.getNickName());
        member.setPhoneNumber(updatedMember.getPhoneNumber());
        member.setPromotionalConsent(updatedMember.getPromotionalConsent());
        return memberRepository.save(member);

    }

}
