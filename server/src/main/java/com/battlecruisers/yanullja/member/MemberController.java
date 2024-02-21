package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.SecurityMember;
import com.battlecruisers.yanullja.member.dto.MemberResponseDto;
import com.battlecruisers.yanullja.member.dto.MemberUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get my information",
        description = "Returns member information for the currently authenticated user",
        responses = {
            @ApiResponse(description = "Successful retrieval of user information",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(description = "Unauthorized if the user is not authenticated", responseCode = "401")
        })
    public MemberResponseDto getMyInfo(
        @AuthenticationPrincipal SecurityMember me) {

        var memberId = me.getId();
        return memberService.getMember(memberId);
    }

    @PutMapping("/me")
    @Operation(summary = "Update my information",
        description = "Updates the member information for the currently authenticated user",
        responses = {
            @ApiResponse(description = "Successful update of user information", responseCode = "200"),
            @ApiResponse(description = "Unauthorized if the user is not authenticated", responseCode = "401")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Member update information",
            required = true,
            content = @Content(schema = @Schema(implementation = MemberUpdateDto.class))))
    public void updateMyInfo(@AuthenticationPrincipal SecurityMember member,
        @RequestBody MemberUpdateDto dto) {
        var memberId = member.getId();
        memberService.updateMember(memberId, dto);
    }

}
