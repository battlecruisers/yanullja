package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.SecurityMember;
import com.battlecruisers.yanullja.member.dto.MemberResponseDto;
import com.battlecruisers.yanullja.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public MemberResponseDto getMyInfo(
        @AuthenticationPrincipal SecurityMember me) {

        var memberId = me.getId();
        return memberService.getMember(memberId);
    }

    @PutMapping("/me")
    public void updateMyInfo(@AuthenticationPrincipal SecurityMember member,
        @RequestBody MemberUpdateDto dto) {
        var memberId = member.getId();
        memberService.updateMember(memberId, dto);
    }

}
