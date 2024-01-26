package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CouponResponseDto {
    // 쿠폰번호
    Long id;
    // 객실id
    Long roomId;
    // 쿠폰명
    String name;
    // 최소주문금액
    BigDecimal minimumPrice;
    // 할인금액
    BigDecimal discountPrice;
    // 할인률
    Double discountRate;
    // 할인한도
    Double discountLimit;
    // 설명
    String description;
    // 사용지역
    @Column
    String region;
    // 숙박형태
    @Column
    @Enumerated(EnumType.STRING)
    RoomType roomType;
    // 쿠폰 유효 여부
    Boolean isValid;
    // 쿠폰 등록 여부
    Boolean isRegistered;
    // 쿠폰 유효기간(시작일)
    LocalDate validityStartDate;
    // 쿠폰 유효기간(종료일)
    LocalDate validityEndDate;
}
