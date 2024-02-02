package com.battlecruisers.yanullja.member.dto;

import com.battlecruisers.yanullja.member.domain.Member;
import lombok.Data;

@Data
public class MemberResponseDto {

    private Long id;
    private String provider;
    private String providerId;
    private String email;
    private String nickName;
    private String phoneNumber;

    public MemberResponseDto(
        Long id,
        String provider,
        String providerId,
        String email,
        String nickName,
        String phoneNumber) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
            member.getId(),
            member.getProvider(),
            member.getProviderId(),
            member.getEmail(),
            member.getNickName(),
            member.getPhoneNumber());
    }
}
