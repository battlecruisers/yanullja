package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
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

    // 쿠폰번호
    public Long id;
    // 객실id
    public Long roomId;
    // 쿠폰명
    public String name;
    // 최소주문금액
    public BigDecimal minimumPrice;
    // 할인금액
    public BigDecimal discountPrice;
    // 할인률
    public Double discountRate;
    // 할인한도
    public BigDecimal discountLimit;
    // 설명
    public String description;
    // 사용지역
    @Column
    public String region;
    // 숙박형태
    @Column
    @Enumerated(EnumType.STRING)
    public RoomType roomType;
    // 쿠폰 유효 여부
    public Boolean isValid;
    // 쿠폰 등록 여부
    public Boolean isRegistered;
    // 쿠폰 유효기간(시작일)
    public LocalDate validityStartDate;
    // 쿠폰 유효기간(종료일)
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
