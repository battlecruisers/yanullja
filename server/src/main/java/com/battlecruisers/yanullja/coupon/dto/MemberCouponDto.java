package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberCouponDto {
    public Long id;
    public Long couponId;
    public Long memberId;
    public Boolean isUsed;

    public static MemberCouponDto from(MemberCoupon memberCoupon) {
        MemberCouponDto memberCouponDto = new MemberCouponDto();
        memberCouponDto.id = memberCoupon.getId();
        memberCouponDto.couponId = memberCoupon.getCoupon().getId();
        memberCouponDto.memberId = memberCoupon.getMember().getId();
        memberCouponDto.isUsed = memberCoupon.getIsUsed();

        return memberCouponDto;
    }
}
