package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // 적용 가능한 전체 쿠폰 조회
    public List<Coupon> getCouponList() {
        return couponRepository.findAll();
    }

    // 하나의 쿠폰 정보 조회

    public Coupon getCoupon(Long id) {
        Optional<Coupon> tempCoupon = couponRepository.findById(id);

        return tempCoupon.orElseThrow();

    }
}
