package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.member.dto.MemberResponseDto;
import com.battlecruisers.yanullja.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getMember(Long memberId) {
        var member = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new MemberNotFoundException(memberId)
            );
        return MemberResponseDto.from(member);
    }

    @Transactional
    public Member updateMember(Long memberId, MemberUpdateDto dto) {
        var member = memberRepository.findById(memberId).orElseThrow(
            () -> new MemberNotFoundException(memberId)
        );

        member.updateMember(dto);

        return memberRepository.save(member);

    }

}
