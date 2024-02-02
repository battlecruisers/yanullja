package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCouponRepository extends
        JpaRepository<MemberCoupon, Long>, CustomMemberCouponRepository {

    // 회원의 쿠폰 사용내역 조회
    List<MemberCoupon> findByMemberAndIsUsed(Member member, boolean isUsed);

    // 쿠폰 사용 여부도 고려해서 조회, 멤버아이디도 필요

    @Query(value =
            "SELECT mc FROM MemberCoupon mc JOIN FETCH mc.coupon c WHERE c.room.id = :roomId "
                    +
                    "and c.isValid = true and mc.isUsed = false and c.isRegistered = true")
    List<MemberCoupon> findByRoomId(@Param("roomId") Long roomId);

    // 회원이 사용 가능한 쿠폰 목록 조회
    @Query(value = "select mc from MemberCoupon mc join fetch mc.coupon where mc.member.id = :memberId and mc.isUsed = false")
    List<MemberCoupon> findMemberCouponsWithCoupon(
            @Param("memberId") Long memberId);

}
