package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    // 회원의 쿠폰 사용내역 조회
    Optional<List<MemberCoupon>> findByMemberAndIsUsed(Member member, boolean isUsed);


}
