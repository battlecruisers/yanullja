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

    // 회원 쿠폰 ID
    @Schema(name = "Member Coupon ID", example = "1")
    public Long id;

    // 회원 쿠폰명
    @Schema(name = "Member Coupon Name", example = "50% Discount Coupon")
    public String name;

    // 할인률
    @Schema(name = "Discount Rate", example = "0.5")
    public BigDecimal discountRate;

    // 고정 할인 금액
    @Schema(name = "Fixed Discount Price", example = "10000")
    public BigDecimal discountPrice;

    // 쿠폰 발행일
    @Schema(name = "Coupon Issuance Date", example = "2024-02-21")
    public LocalDate startDate;

    // 쿠폰 유효기간
    @Schema(name = "Coupon Validity Period", example = "2024-12-31")
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
