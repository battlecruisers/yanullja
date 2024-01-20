package com.battlecruisers.yanullja.coupon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCouponDto {
    private Long id;
    private Long memberId;
    private Long couponId;
    private boolean isUsed;

    // Constructors
    public MemberCouponDto() {
    }

    public MemberCouponDto(Long id, Long memberId, Long couponId, boolean isUsed) {
        this.id = id;
        this.memberId = memberId;
        this.couponId = couponId;
        this.isUsed = isUsed;
    }
}
