package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.room.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final RoomRepository roomRepository;

//    public CouponService(CouponRepository couponRepository) {
//        this.couponRepository = couponRepository;
//    }

    // 적용 가능한 전체 쿠폰 조회
    public List<Coupon> getCouponList() {
        return couponRepository.findAll();
    }

    // 하나의 쿠폰 정보 조회

    public Coupon getCoupon(Long id) {
        Optional<Coupon> tempCoupon = couponRepository.findById(id);

        return tempCoupon.orElseThrow();

    }

    // 쿠폰 생성
    public Long createCoupon() {
        Coupon newCoupon = new Coupon();

        newCoupon.setDescription("좋은 쿠폰");
        newCoupon.setDiscountLimit(10d);
        newCoupon.setDiscountPrice(new BigInteger("5000"));
        newCoupon.setDiscountRate(10d);
        newCoupon.setValid(true);
        newCoupon.setMinimumPrice(new BigInteger("40000"));
        newCoupon.setName("할인 쿠폰");
        newCoupon.setRegion("서울");
        newCoupon.setRoomType(RoomType.DayUse);
        newCoupon.setRoom(roomRepository.findById(2L).orElseThrow());

        Coupon insertCoupon = couponRepository.save(newCoupon);

        return insertCoupon.getId();
    }
}
