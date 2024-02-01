package com.battlecruisers.yanullja.member.domain;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.member.dto.MemberUpdateDto;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String providerId;
    private String email;
    private String nickName;
    private String phoneNumber;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "member")
    private List<MemberCoupon> memberCoupons;

    private Boolean promotionalConsent;

    protected Member(String provider, String providerId, String email,
        String nickName, String phoneNumber, List<MemberCoupon> memberCoupons,
        Boolean promotionalConsent) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.memberCoupons = memberCoupons;
        this.promotionalConsent = promotionalConsent;
    }

    public Member(Long id) {
        this.id = id;
    }

    // 정적 팩토리 메소드
    public static Member createMember(String provider, String providerId,
        String email,
        String nickName, String phoneNumber,
        List<MemberCoupon> memberCoupons, Boolean promotionalConsent) {
        return new Member(provider, providerId, email, nickName, phoneNumber,
            memberCoupons, promotionalConsent);
    }

    public void updateMember(MemberUpdateDto dto) {
        this.email = dto.getEmail();
        this.nickName = dto.getNickName();
        this.phoneNumber = dto.getPhoneNumber();
    }
}
