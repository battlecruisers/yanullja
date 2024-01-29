package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.coupon.dto.CouponDto;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.coupon.exception.BelowMinimumCouponAmountException;
import com.battlecruisers.yanullja.coupon.exception.InvalidCouponException;
import com.battlecruisers.yanullja.room.RoomRepository;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.battlecruisers.yanullja.place.PlaceService.isWeekend;


@RequiredArgsConstructor
@Service
@Transactional
public class CouponService {
    private final CouponRepository couponRepository;
    private final RoomRepository roomRepository;
    // 테스트
    private final MemberCouponRepository memberCouponRepository;


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

    public Long createCoupon() {
        Coupon newCoupon = Coupon.createCoupon("할인 쿠폰", BigDecimal.valueOf(40000),
                BigDecimal.valueOf(5000), 10.0, BigDecimal.valueOf(10.0), "좋은 쿠폰", "서울", RoomType.DayUse,
                roomRepository.findById(2L).orElseThrow(), true, false);

        couponRepository.save(newCoupon);

        return newCoupon.getId();
    }

    // 예약하려는 방이 쿠폰을 사용할 수 있는지 확인
    public void validateCoupon(MemberCoupon memberCoupon, BigDecimal price) {
        BigDecimal minimalPrice = couponRepository
                .findById(memberCoupon.getCoupon().getId())
                .orElseThrow(() -> new InvalidCouponException(memberCoupon.getCoupon().getId()))
                .getMinimumPrice();

        // 객실의 숙박비용이 쿠폰의 최소사용금액을 넘지 않을 경우 예외 발생
        if (price.compareTo(minimalPrice) < 0) {
            throw new BelowMinimumCouponAmountException(memberCoupon.getId());
        }

    }

    // 쿠폰 할인금액 계산
    public BigDecimal calculateDiscountedPrice(MemberCouponDto memberCouponDto, BigDecimal originalPrice) {

        // 멤버 쿠폰에서 쿠폰 정보를 가져옵니다.
        Coupon coupon = couponRepository.findById(memberCouponDto.getCouponId()).orElseThrow();
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

    // 가장 할인을 많이 해주는 쿠폰 반환
    public List<CouponDto> findMostDiscountedCoupon(List<MemberCouponDto> list, Room room, LocalDate localDate, RoomType roomType) {
        List<CouponDto> mostDiscountedCoupons = new ArrayList<>();
        // 최대할인가를 0으로 초기화
        BigDecimal maxDiscount = BigDecimal.ZERO;
        if (isWeekend(localDate)) {
            // 현재 날짜에 따라 가격을 다르게 계산
            maxDiscount = (roomType.equals(RoomType.Stay)) ?
                    calculateDiscountedPrice(list.get(0), BigDecimal.valueOf(room.getWeekendStayPrice())) :
                    calculateDiscountedPrice(list.get(0), BigDecimal.valueOf(room.getWeekendRentPrice()));
        } else {
            maxDiscount = (roomType.equals(RoomType.Stay)) ?
                    calculateDiscountedPrice(list.get(0), BigDecimal.valueOf(room.getWeekdayStayPrice())) :
                    calculateDiscountedPrice(list.get(0), BigDecimal.valueOf(room.getWeekdayRentPrice()));
        }


        // 리스트르에서 회원 쿠폰 정보를 읽어오면서 최대할인율을 계산
        for (MemberCouponDto m : list) {

            BigDecimal discount = calculateDiscountedPrice(
                    m,
                    isWeekend(localDate) ?
                            (roomType.equals(RoomType.Stay) ?
                                    BigDecimal.valueOf(room.getWeekendStayPrice()) :
                                    BigDecimal.valueOf(room.getWeekendRentPrice())) :
                            (roomType.equals(RoomType.Stay) ?
                                    BigDecimal.valueOf(room.getWeekdayStayPrice()) :
                                    BigDecimal.valueOf(room.getWeekdayRentPrice()))
            );

            if (discount.compareTo(maxDiscount) < 0) {
                mostDiscountedCoupons.clear();
                maxDiscount = discount;

                Coupon coupon = couponRepository
                        .findById(m.getCouponId())
                        .orElseThrow();

                CouponDto couponDto = CouponDto.from(coupon);

                mostDiscountedCoupons.add(couponDto);
            } else if (discount.compareTo(maxDiscount) == 0) {
                Coupon coupon = couponRepository
                        .findById(m.getCouponId())
                        .orElseThrow();

                CouponDto couponDto = CouponDto.from(coupon);

                mostDiscountedCoupons.add(couponDto);
            }


        }

        return mostDiscountedCoupons;
    }

    // 테스트용
    public List<MemberCouponDto> getAvailableCouponsByRoomId(Long roomId) {
        List<MemberCoupon> list = memberCouponRepository.findByRoomId(roomId);
        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();
        for (MemberCoupon m : list) {
            memberCouponDtos.add(MemberCouponDto.from(m));
        }
        return memberCouponDtos;
    }


    public Room getRoomInfo(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow();
    }
}
