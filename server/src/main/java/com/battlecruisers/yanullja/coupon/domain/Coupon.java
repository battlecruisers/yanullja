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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    private boolean isValid;

    // 쿠폰 등록 여부
    private boolean isRegistered;

    // 쿠폰 유효기간(시작일)
    private LocalDate validityStartDate;

    // 쿠폰 유효기간(종료일)
    private LocalDate validityEndDate;


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
