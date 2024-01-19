package com.battlecruisers.yanullja.coupon.domain;

import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Coupon {
    // 숙소 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;

    @OneToMany(mappedBy = "coupon")
    List<MemberCoupon> memberCouponList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // 최소주문금액
    private int minimumPrice;
    // 할인금액
    private int discountPrice;
    // 할인률
    private int discountRate;
    // 할인한도
    private int discountLimit;
    // 설명
    private String description;
    // 사용지역
    @Column
    private String region;
    // 숙박형태
    @Column
    private boolean type;
    // 회원 아이디
//    @ManyToOne
//    private Member member;
    // 상태
//    @Enumerated(EnumType.STRING)
    private boolean isValid;

}
