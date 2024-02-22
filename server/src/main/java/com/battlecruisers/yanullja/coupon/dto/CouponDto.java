package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CouponDto {

    @Schema(name = "쿠폰번호", example = "1")
    public Long id;

    @Schema(name = "객실id", example = "3")
    public Long roomId;

    @Schema(name = "쿠폰명", example = "50%할인쿠폰")
    public String name;

    @Schema(name = "최소주문금액", example = "20000")
    public BigDecimal minimumPrice;

    @Schema(name = "할인금액", example = "10000")
    public BigDecimal discountPrice;

    @Schema(name = "할인률", example = "0.5")
    public Double discountRate;

    @Schema(name = "할인한도", example = "5000")
    public BigDecimal discountLimit;

    @Schema(name = "설명", example = "최소주문금액 20000원 이상 시 사용 가능한 50% 할인 쿠폰입니다.")
    public String description;

    @Schema(name = "사용지역", example = "서울")
    public String region;

    @Schema(name = "숙박형태", example = "HOTEL")
    public RoomType roomType;

    @Schema(name = "쿠폰 유효 여부", example = "true")
    public Boolean isValid;

    @Schema(name = "쿠폰 등록 여부", example = "true")
    public Boolean isRegistered;

    @Schema(name = "쿠폰 유효기간(시작일)", example = "2024-02-21")
    public LocalDate validityStartDate;

    @Schema(name = "쿠폰 유효기간(종료일)", example = "2024-12-31")
    public LocalDate validityEndDate;


    public static CouponDto from(Coupon coupon) {
        CouponDto couponDto = new CouponDto();
        couponDto.setId(coupon.getId());
        couponDto.setRoomId(coupon.getRoom().getId());
        couponDto.setName(coupon.getName());
        couponDto.setMinimumPrice(coupon.getMinimumPrice());
        couponDto.setDiscountPrice(coupon.getDiscountPrice());
        couponDto.setDiscountRate(coupon.getDiscountRate());
        couponDto.setDiscountLimit(coupon.getDiscountLimit());
        couponDto.setDescription(coupon.getDescription());
        couponDto.setRegion(coupon.getRegion());
        couponDto.setRoomType(coupon.getRoomType());
        couponDto.setIsValid(coupon.getIsValid());
        couponDto.setIsRegistered(coupon.getIsRegistered());
        couponDto.setValidityStartDate(coupon.getValidityStartDate());
        couponDto.setValidityEndDate(coupon.getValidityEndDate());
        return couponDto;
    }

}
