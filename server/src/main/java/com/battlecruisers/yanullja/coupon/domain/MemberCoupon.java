package com.battlecruisers.yanullja.coupon.domain;

import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "prevent duplication of membercoupon",
                        columnNames = {"member_id", "coupon_id"}
                )
        }
)
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 회원쿠폰 아이디
    private Long id;

    // 회원 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 쿠폰 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    // 상태
    private boolean isUsed;
}
