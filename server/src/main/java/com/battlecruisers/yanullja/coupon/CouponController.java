package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService){
        this.couponService = couponService;
    }

    @GetMapping("/")
    // 전체 쿠폰 목록 조회
    public List<Coupon> list(){
        return couponService.getCouponList();
    }

    @GetMapping("/{couponId}")
    // 하나의 쿠폰 조회
    public ResponseEntity<Coupon> coupon(@PathVariable Long couponId){
        Coupon testCoupon = couponService.getCoupon(couponId);
        log.info("testCoupon={}", testCoupon.toString());
        return new ResponseEntity<>(testCoupon, HttpStatus.OK);
    }



}
