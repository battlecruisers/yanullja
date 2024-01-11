package com.battlecruisers.yanullja.coupon.domain;

import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hibernate.mapping.ToOne;

@Entity
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 회원쿠폰 아이디
    private Long id;

    // 회원 아이디
    @ManyToOne
    private Member member;

    // 쿠폰 아이디
    @ManyToOne
    private Coupon coupon;

    // 상태
    @Enumerated(EnumType.ORDINAL)
    private Status status;



}
