package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;

import java.util.Optional;

public interface CustomMemberCouponRepository {

    Optional<MemberCoupon> queryMemberCoupon(Long memberId);
}
