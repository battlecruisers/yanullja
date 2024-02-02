package com.battlecruisers.yanullja.coupon.exception;

public class InvalidCouponException extends RuntimeException {

    public InvalidCouponException(Long couponId) {
        super("쿠폰(쿠폰번호: " + couponId + ")는 유효하지 않은 쿠폰입니다.");
    }
}
