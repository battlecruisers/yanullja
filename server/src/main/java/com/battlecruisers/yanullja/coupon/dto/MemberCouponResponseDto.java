package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberCouponResponseDto {

    MemberCouponDto memberCouponDto;
    CouponDto couponDto;

    public static MemberCouponResponseDto from(MemberCoupon memberCoupon) {
        MemberCouponDto memberCouponDto = MemberCouponDto.from(memberCoupon);
        CouponDto couponDto = CouponDto.from(memberCoupon.getCoupon());
        return new MemberCouponResponseDto(memberCouponDto, couponDto);
    }
}
