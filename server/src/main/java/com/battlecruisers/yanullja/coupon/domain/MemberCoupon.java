package com.battlecruisers.yanullja.coupon.domain;

import com.battlecruisers.yanullja.base.BaseDate;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "prevent duplication of membercoupon",
                        columnNames = {"member_id", "coupon_id"}
                )
        }
)
public class MemberCoupon extends BaseDate {
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
    private Boolean isUsed;

    // protected 생성자
    protected MemberCoupon(Member member, Coupon coupon, Boolean isUsed) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
    }

    // 정적 팩토리 메서드
    public static MemberCoupon createMemberCoupon(Member member, Coupon coupon, Boolean isUsed) {
        return new MemberCoupon(member, coupon, isUsed);
    }

    // 쿠폰 상태 변경
    public void updateUsageStatus() {
        this.isUsed = true;
    }

    // 가격을 제일 많이 할인해주는 쿠폰 반환


}
