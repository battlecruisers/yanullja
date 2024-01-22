package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("")
    // 전체 쿠폰 목록 조회
    public List<Coupon> list() {
        return couponService.getCouponList();
    }

    @GetMapping("/{couponId}")
    // 하나의 쿠폰 조회

    public ResponseEntity<Coupon> coupon(@PathVariable(name = "couponId") Long id) {
        Coupon testCoupon = couponService.getCoupon(id);
        log.info("testCoupon={}", testCoupon.toString());
        return new ResponseEntity<>(testCoupon, HttpStatus.OK);
    }


    // 쿠폰 생성 테스트
    @PostMapping
    public ResponseEntity<Long> insert(){
        Long couponId = couponService.createCoupon();
        return new ResponseEntity<>(couponId, HttpStatus.OK);
    }

}
