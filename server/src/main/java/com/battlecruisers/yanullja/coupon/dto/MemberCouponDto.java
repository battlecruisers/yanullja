package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberCouponDto {

    public Long id;
    public String name;
    public BigDecimal discountRate;
    public BigDecimal discountPrice;

    public static MemberCouponDto from(MemberCoupon memberCoupon) {
        MemberCouponDto memberCouponDto = new MemberCouponDto();
        memberCouponDto.id = memberCoupon.getId();
        memberCouponDto.name = memberCoupon.getCoupon().getName();
        memberCouponDto.discountRate = BigDecimal.valueOf(
            memberCoupon.getCoupon().getDiscountRate());
        memberCouponDto.discountPrice = memberCoupon.getCoupon()
            .getDiscountPrice();
        return memberCouponDto;
    }
}
