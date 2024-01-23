package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final RoomRepository roomRepository;

    // 하나의 쿠폰 정보 조회

    // 적용 가능한 전체 쿠폰 조회
    public List<Coupon> getCouponList() {
        return couponRepository.findAll();
    }

    public Coupon getCoupon(Long id) {
        Optional<Coupon> tempCoupon = couponRepository.findById(id);

        return tempCoupon.orElseThrow();

    }

    // 쿠폰 생성
    public Long createCoupon() {
        Coupon newCoupon = Coupon.builder()
                .name("쿠폰 이름")
                .minimumPrice(new BigDecimal("10000"))
                .discountPrice(new BigDecimal("5000"))
                .discountRate(10.0)
                .discountLimit(100.0)
                .description("좋은 쿠폰")
                .region("서울")
                .roomType(RoomType.DayUse)
                .room(roomRepository.findById(2L).orElseThrow())
                .isValid(true)
                .isRegistered(false)
                .build();


        Coupon insertCoupon = couponRepository.save(newCoupon);

        return insertCoupon.getId();

    }


}
