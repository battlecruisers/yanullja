package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCouponRepository extends
    JpaRepository<MemberCoupon, Long> {

    // 회원의 쿠폰 사용내역 조회
    @Query(value =
        "SELECT mc FROM MemberCoupon mc JOIN FETCH mc.coupon c WHERE mc.isUsed = :isUsed "
            +
            "and mc.member.id = :memberId")
    List<MemberCoupon> findByMemberIdAndIsUsed(@Param("memberId") Long memberId,
        @Param("isUsed") boolean isUsed);

    @Query(value =
        "SELECT mc FROM MemberCoupon mc JOIN FETCH mc.coupon c WHERE c.room.id = :roomId "
            +
            "and c.isValid = true and c.isRegistered = true and mc.isUsed = false "
            +
            "and mc.member.id = :memberId")
    List<MemberCoupon> findByRoomIdAndMemberId(@Param("roomId") Long roomId,
        @Param("memberId") Long memberId);

    // 회원이 사용 가능한 쿠폰 목록 조회
    @Query(value = "select mc from MemberCoupon mc join fetch mc.coupon where mc.member.id = :memberId and mc.isUsed = false")
    List<MemberCoupon> findMemberCouponsWithCoupon(
        @Param("memberId") Long memberId);

}
