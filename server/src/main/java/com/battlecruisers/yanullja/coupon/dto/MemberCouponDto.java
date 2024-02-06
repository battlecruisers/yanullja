package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class MemberCouponDto {

    public Long id;
    public String name;
    public BigDecimal discountRate;
    public BigDecimal discountPrice;
    public LocalDate startDate;
    public LocalDate endDate;

    public static MemberCouponDto from(MemberCoupon memberCoupon) {
        MemberCouponDto memberCouponDto = new MemberCouponDto();
        memberCouponDto.id = memberCoupon.getId();

        Coupon coupon = memberCoupon.getCoupon();

        memberCouponDto.name = coupon.getName();

        memberCouponDto.discountRate = BigDecimal.valueOf(coupon.getDiscountRate());

        memberCouponDto.discountPrice = coupon.getDiscountPrice();

        memberCouponDto.startDate = coupon.getValidityStartDate();

        memberCouponDto.endDate = coupon.getValidityEndDate();

        return memberCouponDto;
    }
}
