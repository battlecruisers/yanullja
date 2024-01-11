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
    @Column(name = "coupon_id")
    private Long id;
    @Column(name = "coupon_name")
    private String name;
    // 최소주문금액
    private int minimum_price;
    // 할인금액
    private int discount_price;
    // 할인률
    private int discount_rate;
    // 할인한도
    private int discount_limit;
    // 설명
    private String description;
    // 사용지역
    @Column(name = "use_region")
    private String region;
    // 숙박형태
    @Column(name = "accom_type")
    private String type;

    // 상태
    private Short status;

    // 회원 아이디
//    private String user_id;

    // 숙소 아이디
    @ManyToOne
    Room room;

}
