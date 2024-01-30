package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.coupon.dto.CouponDto;
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
                BigDecimal.valueOf(5000), 10.0, BigDecimal.valueOf(10.0), "좋은 쿠폰", "서울", RoomType.RENT,
                roomRepository.findById(2L).orElseThrow(), true, false);

        couponRepository.save(newCoupon);

        return newCoupon.getId();
    }

    // 예약하려는 방이 쿠폰을 사용할 수 있는지 확인
    public void validateCoupon(Coupon coupon, BigDecimal price) {
        BigDecimal minimalPrice = coupon.getMinimumPrice();
//        couponRepository
//                .findById(memberCoupon.getCoupon().getId())
//                .orElseThrow(() -> new InvalidCouponException(memberCoupon.getCoupon().getId()))
//                .getMinimumPrice();


        // 객실의 숙박비용이 쿠폰의 최소사용금액을 넘지 않을 경우 예외 발생
        if (price.compareTo(minimalPrice) < 0) {
//            throw new BelowMinimumCouponAmountException(memberCoupon.getId());
            // 수정 가능성 있음
            throw new BelowMinimumCouponAmountException(coupon.getId());
        }

    }

    // 쿠폰 할인금액 적용 결과 계산
    public BigDecimal calculateDiscountedPrice(Coupon coupon, BigDecimal originalPrice) {

        // 멤버 쿠폰에서 쿠폰 정보를 가져옵니다.
//        Coupon coupon = couponRepository.findById(memberCouponDto.getCouponId()).orElseThrow();
        return Coupon.getCalculateDiscountedPrice(originalPrice, coupon);
    }


    // 쿠폰 적용시 할인 받는 금액 반환
    public BigDecimal calculateCouponDiscountAmount(MemberCoupon memberCoupon, BigDecimal originalPrice) {
        // 멤버 쿠폰에서 쿠폰 정보를 가져옵니다.
        Coupon coupon = couponRepository.findById(memberCoupon.getCoupon().getId()).orElseThrow();

        return Coupon.getCalculateCouponDiscountAmount(originalPrice, coupon);


    }


    // 가장 할인을 많이 해주는 쿠폰 반환
    public List<CouponDto> findMostDiscountedCoupon(Long roomId) {
        // 사용 가능한 쿠폰목록
        List<MemberCoupon> availableCoupons = this.getAvailableCouponsByRoomId(roomId);

        // 최대 할인 쿠폰(최대로 할인 가능한 쿠폰이 2개 이상 존재할 수 있기 때문에 리스트를 사용)
        List<CouponDto> mostDiscountedCoupons = new ArrayList<>();

        // 쿠폰 할인 적용 대상인 객실의 정보


        // 최대로 할인 받는 금액을 초기화
        BigDecimal maxDiscountAmount = BigDecimal.ZERO;

        // 기준날짜 지정
        LocalDate nowTime = LocalDate.now();

        // 리스트르에서 회원 쿠폰 정보를 읽어오면서 최대 할인 쿠폰 목록을 생성한다.
        for (MemberCoupon m : availableCoupons) {

            BigDecimal discountAmount = calculateCouponDiscountAmount(
                    m,
                    isWeekend(nowTime) ?
                            (m.getCoupon().getRoomType().equals(RoomType.STAY) ?
                                    BigDecimal.valueOf(m.getCoupon().getRoom().getWeekendStayPrice()) :
                                    BigDecimal.valueOf(m.getCoupon().getRoom().getWeekendRentPrice())) :
                            (m.getCoupon().getRoomType().equals(RoomType.STAY) ?
                                    BigDecimal.valueOf(m.getCoupon().getRoom().getWeekdayStayPrice()) :
                                    BigDecimal.valueOf(m.getCoupon().getRoom().getWeekdayRentPrice()))
            );

            if (discountAmount.compareTo(maxDiscountAmount) > 0) {
                mostDiscountedCoupons.clear();
                maxDiscountAmount = discountAmount;

                Coupon coupon = couponRepository
                        .findById(m.getCoupon().getId())
                        .orElseThrow();

                CouponDto couponDto = CouponDto.from(coupon);

                mostDiscountedCoupons.add(couponDto);
            } else if (discountAmount.compareTo(maxDiscountAmount) == 0) {
                Coupon coupon = couponRepository
                        .findById(m.getCoupon().getId())
                        .orElseThrow();

                CouponDto couponDto = CouponDto.from(coupon);

                mostDiscountedCoupons.add(couponDto);
            }


        }

        return mostDiscountedCoupons;
    }


    public List<MemberCoupon> getAvailableCouponsByRoomId(Long roomId) {
        List<MemberCoupon> availableCoupons = memberCouponRepository.findByRoomId(roomId);
//        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();
//        for (MemberCoupon m : list) {
//            memberCouponDtos.add(MemberCouponDto.from(m));
//        }
//        return memberCouponDtos;
        return availableCoupons;
    }


    public Room getRoomInfo(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow();
    }
}
