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

    // 회원이 쿠폰을 등록한 적이 있는지 조회(이미 등록한 쿠폰 중복 등록을 방지하기 위함)
    Optional<Long> countByCouponAndMember(Coupon coupon, Member member);
}
