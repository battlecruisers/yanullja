package com.battlecruisers.yanullja.coupon.exception;

public class InvalidAccessException extends RuntimeException {

    public InvalidAccessException() {
        super("부적절한 접근입니다.");
    }
}
