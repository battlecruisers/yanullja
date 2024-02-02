package com.battlecruisers.yanullja.coupon.exception;

public class NoAvailableCouponsException extends RuntimeException {

    public NoAvailableCouponsException() {
        super("사용 가능한 쿠폰이 존재하지 않습니다.");
    }
}
