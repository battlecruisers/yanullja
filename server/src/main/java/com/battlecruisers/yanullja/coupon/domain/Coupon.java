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
    private BigDecimal discountLimit;
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
                     Double discountRate, BigDecimal discountLimit, String description,
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
                                      Double discountRate, BigDecimal discountLimit, String description,
                                      String region, RoomType roomType, Room room, boolean isValid, boolean isRegistered) {
        return new Coupon(name, minimumPrice, discountPrice, discountRate, discountLimit, description, region, roomType, room, isValid, isRegistered);
    }

    // 할인이 적용된 가격 반환
    public static BigDecimal getCalculateDiscountedPrice(BigDecimal originalPrice, Coupon coupon) {
        // 쿠폰의 고정 할인 금액
        BigDecimal discountPrice = coupon.getDiscountPrice();
        // 쿠폰의 할인율
        BigDecimal discountRate = BigDecimal.valueOf(coupon.getDiscountRate());

        // 고정 할인 금액을 적용합니다.
        BigDecimal priceAfterFixedDiscount = originalPrice
                .subtract(discountPrice)
                .max(BigDecimal.ZERO);

        // 할인율이 0이면, 고정 할인 금액을 적용한 가격을 반환합니다.
        if (discountRate.compareTo(BigDecimal.ZERO) == 0) {
            return priceAfterFixedDiscount;
        }

        // 할인율을 적용한 할인 금액을 계산합니다.
        BigDecimal discountAmount = originalPrice.multiply(discountRate.divide(BigDecimal.valueOf(100)));
        BigDecimal priceAfterPercentageDiscount = originalPrice.subtract(discountAmount);

        // 할인 금액이 할인 제한을 초과하면, 할인 제한만큼만 할인을 적용합니다.
        if (discountAmount.compareTo(coupon.getDiscountLimit()) > 0) {
            return priceAfterFixedDiscount.subtract(coupon.getDiscountLimit());
        }

        // 할인율을 적용한 가격을 반환합니다.
        return priceAfterPercentageDiscount;
    }

    public static BigDecimal getCalculateCouponDiscountAmount(BigDecimal originalPrice, Coupon coupon) {
        // 쿠폰의 고정 할인 금액
        BigDecimal discountPrice = coupon.getDiscountPrice();
        // 쿠폰의 할인율
        BigDecimal discountRate = BigDecimal.valueOf(coupon.getDiscountRate());

        // 할인율 적용시 얼마나 할인받을 수 있는지 계산
        BigDecimal discountRatePrice = BigDecimal.ZERO;
        // 0이 아닐 경우에만 계산한다.
        if (discountRate.compareTo(BigDecimal.ZERO) != 0) {
            discountRatePrice = originalPrice.multiply(discountRate.divide(BigDecimal.valueOf(100)));
        }

        // 할인받을 수 있는 금액이 최대 할인 한도를 초과할 경우 최대 할인 한도만큼을 할인금액으로 하여 반환
        return (discountPrice.add(discountRatePrice)
                .compareTo(coupon.getDiscountLimit()) > 0) ? coupon.getDiscountLimit() : discountPrice.add(discountRatePrice);

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
