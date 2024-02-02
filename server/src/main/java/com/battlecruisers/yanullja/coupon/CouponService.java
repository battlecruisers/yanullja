package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.coupon.dto.CouponDto;
import com.battlecruisers.yanullja.coupon.exception.BelowMinimumCouponAmountException;
import com.battlecruisers.yanullja.coupon.exception.InvalidCouponException;
import com.battlecruisers.yanullja.room.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;
    private final RoomRepository roomRepository;
    // 테스트
    private final MemberCouponRepository memberCouponRepository;

    // 쿠폰 적용시 할인 받는 금액 반환

    // 할인이 적용된 가격 반환
    public static BigDecimal getCalculateDiscountedPrice(
            BigDecimal originalPrice,
            BigDecimal discountPrice,
            BigDecimal discountRate,
            BigDecimal discountLimit) {
        // 할인이 적용된 가격읆 담을 변수
        BigDecimal discountedPrice = BigDecimal.ZERO;
        // 할인 받는 금액
        BigDecimal discountAmount = BigDecimal.ZERO;

        discountPrice = discountPrice.setScale(0);

        // 고정 할인 금액이 0원일 경우 할인율을, 할인율이 0일 경우 고정 할인 금액을 적용합니다.
        if (discountPrice.equals(BigDecimal.ZERO)) {
            // 할인율 적용시 할인받는 금액
            discountAmount =
                    originalPrice
                            .multiply(BigDecimal.ONE.subtract(
                                    (BigDecimal.valueOf(100).subtract(discountRate)).divide(BigDecimal.valueOf(100))));

            // 할인율 적용 시  사용하려는 쿠폰의 최대할인한도를 초과했을 경우, 최대할인한도에 해당하는 금액만큼만 할인을 적용합니다.
            if (discountAmount.compareTo(discountLimit) > 0) {
                discountedPrice =
                        originalPrice.subtract(discountLimit);
            } else {
                // 할인 적용된 결과를 반환
                discountedPrice = originalPrice.subtract(discountAmount);
            }
        } else if (discountRate.equals(0.0)) {
            // 고정할인금액이 적용된 결과를 반환
            discountedPrice = originalPrice.subtract(discountPrice);
        }
//        return discountedPrice.setScale(0);
        return discountedPrice;
    }

    // 쿠폰 할인금액 적용 결과 계산
//    public BigDecimal calculateDiscountedPrice(Coupon coupon, BigDecimal originalPrice) {
//        return this
//                .getCalculateDiscountedPrice(originalPrice, coupon.getDiscountPrice(), BigDecimal.valueOf(coupon.getDiscountRate())
//                        , coupon.getDiscountLimit());
//    }

//    public BigDecimal calculateCouponDiscountAmount(MemberCoupon memberCoupon, BigDecimal originalPrice) {
//        // 멤버 쿠폰에서 쿠폰 정보를 가져옵니다.
//        Coupon coupon = couponRepository.findById(memberCoupon.getCoupon().getId()).orElseThrow();
//
//        return this.getCalculateCouponDiscountAmount(originalPrice, coupon);
//
//
//    }


    // 적용 가능한 전체 쿠폰 조회
    public List<CouponDto> getCouponList() {
        List<Coupon> couponList = couponRepository.findAll();

        // 쿠폰목록들을 DTO에 담아 반환한다.
        List<CouponDto> responseDtos = new ArrayList<>();

        for (Coupon c : couponList) {
            CouponDto dto = CouponDto.from(c);
            responseDtos.add(dto);
        }

        return responseDtos;

    }

    // 하나의 쿠폰 정보 조회
    public CouponDto getCoupon(Long id) {
        Coupon coupon = couponRepository
                .findById(id)
                .orElseThrow(() -> new InvalidCouponException(id));

        return CouponDto.from(coupon);
    }

    // 쿠폰 생성

    public Long doNotUse_CreateCoupon() {
        Coupon newCoupon = Coupon.createCoupon("할인 쿠폰",
                BigDecimal.valueOf(40000),
                BigDecimal.valueOf(5000), 10.0, BigDecimal.valueOf(10.0), "좋은 쿠폰",
                "서울", RoomType.RENT,
                roomRepository.findById(2L).orElseThrow(), true, false);

        couponRepository.save(newCoupon);

        return newCoupon.getId();
    }

    // 예약하려는 방이 쿠폰을 사용할 수 있는지 확인
    public void validateCoupon(BigDecimal minimalPrice, BigDecimal price,
                               Coupon coupon) {
//        BigDecimal minimalPrice = coupon.getMinimumPrice();
//        couponRepository
//                .findById(memberCoupon.getCoupon().getId())
//                .orElseThrow(() -> new InvalidCouponException(memberCoupon.getCoupon().getId()))
//                .getMinimumPrice();

        // 등록되지 않은 쿠폰이거나 유효하지 않은 쿠폰일 경우 예외발생
        if (!coupon.getIsValid() && !coupon.getIsRegistered()) {
            throw new InvalidCouponException(coupon.getId());
        }

        // 객실의 숙박비용이 쿠폰의 최소사용금액을 넘지 않을 경우 예외 발생
        if (price.compareTo(minimalPrice) < 0) {
//            throw new BelowMinimumCouponAmountException(memberCoupon.getId());
            // 수정 가능성 있음
            throw new BelowMinimumCouponAmountException(coupon.getId());
        }

    }


}
