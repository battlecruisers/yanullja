package com.battlecruisers.yanullja.coupon.exception;

public class AlreadyUsedException extends RuntimeException {

    public AlreadyUsedException(long couponId) {
        super("해당 쿠폰(쿠폰번호: " + couponId + ")은 이미 사용한 쿠폰입니다.");
    }
}
