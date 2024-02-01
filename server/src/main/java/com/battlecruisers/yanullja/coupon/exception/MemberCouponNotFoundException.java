package com.battlecruisers.yanullja.coupon.exception;

import java.util.NoSuchElementException;

public class MemberCouponNotFoundException extends NoSuchElementException {

    public MemberCouponNotFoundException(long memberCouponId) {
        super("쿠폰(쿠폰번호:" + memberCouponId + ")정보를 가져오는 중 오류 발생");
    }
}
