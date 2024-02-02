package com.battlecruisers.yanullja.coupon.exception;

public class BelowMinimumCouponAmountException extends RuntimeException {

    public BelowMinimumCouponAmountException(Long id) {
        super(id + "번 쿠폰의 최소사용금액 미달");
    }
}
