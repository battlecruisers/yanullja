package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.dto.CouponResponseDto;
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
    public ResponseEntity<List<CouponResponseDto>> list() {
        List<CouponResponseDto> test = couponService.getCouponList();
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @GetMapping("/{couponId}")
    // 하나의 쿠폰 조회

    public ResponseEntity<CouponResponseDto> coupon(@PathVariable(name = "couponId") Long id) {
        CouponResponseDto couponResponseDto = couponService.getCoupon(id);
        log.info("testCoupon={}", couponResponseDto.toString());
        return new ResponseEntity<>(couponResponseDto, HttpStatus.OK);
    }


    // 쿠폰 생성 테스트
    @PostMapping
    public ResponseEntity<Long> insert() {
        Long couponId = couponService.createCoupon();
        return new ResponseEntity<>(couponId, HttpStatus.OK);
    }

}
