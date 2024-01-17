package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Member updateMyInfo(@Valid @RequestBody Member member) {
        var memberId = 1L;
        return memberService.updateMember(memberId, member);
    }

}
