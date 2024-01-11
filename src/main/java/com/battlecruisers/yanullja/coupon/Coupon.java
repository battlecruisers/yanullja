package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.room.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponId")
    private Long id;
    @Column(name = "couponName")
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
    @Column(name = "useRegion")
    private String region;
    // 숙박형태
    @Column(name = "accomType")
    private String type;

    // 상태
    private Short status;

    // 회원 아이디
//    private String user_id;

    // 숙소 아이디
    @ManyToOne
    Room room;

}
