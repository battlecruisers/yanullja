package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class MemberCouponDto {

    @Schema(name = "회원 쿠폰 id", example = "1")
    public Long id;

    @Schema(name = "회원 쿠폰명", example = "50%할인쿠폰")
    public String name;

    @Schema(name = "할인률", example = "0.5")
    public BigDecimal discountRate;

    @Schema(name = "고정할인금액", example = "10000")
    public BigDecimal discountPrice;

    @Schema(name = "쿠폰 발행일", example = "2024-02-21")
    public LocalDate startDate;

    @Schema(name = "쿠폰 유효기간", example = "2024-12-31")
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
