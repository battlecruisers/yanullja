package com.battlecruisers.yanullja.coupon.exception;

public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException(long couponId) {
        super("해당 쿠폰(쿠폰번호: " + couponId + ")은 이미 등록된 상태입니다.");
    }
}
