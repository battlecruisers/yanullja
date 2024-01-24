package com.battlecruisers.yanullja.coupon.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coupon extends BaseDate {
    // 숙소 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @Nullable
    Room room;

    @OneToMany(mappedBy = "coupon")
    List<MemberCoupon> memberCouponList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    // 최소주문금액
    private BigDecimal minimumPrice;
    // 할인금액
    private BigDecimal discountPrice;
    // 할인률
    private Double discountRate;
    // 할인한도
    private Double discountLimit;
    // 설명
    private String description;
    // 사용지역
    @Column
    private String region;

    // 숙박형태
    @Column
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    // 쿠폰 유효 여부
    private Boolean isValid;

    // 쿠폰 등록 여부
    private Boolean isRegistered;

    // 쿠폰 유효기간(시작일)
    private LocalDate validityStartDate;

    // 쿠폰 유효기간(종료일)
    private LocalDate validityEndDate;

    // 생성자
    protected Coupon(String name, BigDecimal minimumPrice, BigDecimal discountPrice,
                     Double discountRate, Double discountLimit, String description,
                     String region, RoomType roomType, Room room, boolean isValid, boolean isRegistered) {
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.discountLimit = discountLimit;
        this.description = description;
        this.region = region;
        this.roomType = roomType;
        this.room = room;
        this.isValid = isValid;
        this.isRegistered = isRegistered;
        // 이 곳에는 추가적인 초기화 로직이 있다면 추가할 수 있습니다.
    }

    // 연관관계 매핑용 생성자
    public Coupon(Long couponId) {
        this.id = couponId;
    }

    // 쿠폰 정보 생성 메서드
    public static Coupon createCoupon(String name, BigDecimal minimumPrice, BigDecimal discountPrice,
                                      Double discountRate, Double discountLimit, String description,
                                      String region, RoomType roomType, Room room, boolean isValid, boolean isRegistered) {
        return new Coupon(name, minimumPrice, discountPrice, discountRate, discountLimit, description, region, roomType, room, isValid, isRegistered);
    }

    // DB에 데이터가 저장되기 전해 실행되는 로직
    @PrePersist
    public void setValidity() {
        // 시작일자는 현재 날짜 기준, 종료일자는 현재 날짜 기준 + 2주
        LocalDate currentDate = LocalDate.now();

        this.validityStartDate = currentDate;

        this.validityEndDate = currentDate.plusWeeks(2);
    }

    // 쿠폰 등록 여부 변경
    public void changeRegistrationStatus() {
        this.isRegistered = true;
    }
}
