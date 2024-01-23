package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.member.dto.MemberUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public Member getMyInfo() {
        var memberId = 1L;
        return memberService.getMember(memberId);
    }

    @PutMapping("/me")
    public Member updateMyInfo(@Valid @RequestBody Member member, @RequestBody MemberUpdateDto dto) {
        var memberId = 1L;
        return memberService.updateMember(memberId, dto);
    }

}
