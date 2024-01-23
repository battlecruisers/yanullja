package com.battlecruisers.yanullja.coupon.exception;

public class CouponUsageHistoryNotFoundException extends Throwable {
    public CouponUsageHistoryNotFoundException(Long memberId) {
        super("해당 유저(id:" + memberId + ")의 사용내역이 존재하지 않습니다.");
    }
}
