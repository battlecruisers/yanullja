package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.battlecruisers.yanullja.coupon.domain.QCoupon.coupon;
import static com.battlecruisers.yanullja.coupon.domain.QMemberCoupon.memberCoupon;
import static com.battlecruisers.yanullja.member.domain.QMember.member;

@RequiredArgsConstructor
public class CustomMemberCouponRepositoryImpl implements CustomMemberCouponRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberCoupon> queryMemberCoupon(Long memberCouponId) {
        MemberCoupon findMemberCoupon = queryFactory.selectFrom(memberCoupon)
                .leftJoin(memberCoupon.member, member).fetchJoin()
                .leftJoin(memberCoupon.coupon, coupon).fetchJoin()
                .where(memberCoupon.id.eq(memberCouponId))
                .fetchOne();
        return Optional.ofNullable(findMemberCoupon);
    }
}
