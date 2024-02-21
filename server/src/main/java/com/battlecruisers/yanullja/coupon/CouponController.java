package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.dto.CouponDto;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "전체 쿠폰 목록 조회")
    @GetMapping("")
    // 전체 쿠폰 목록 조회
    public ResponseEntity<List<CouponDto>> list() {
        List<CouponDto> test = couponService.getCouponList();
        return new ResponseEntity<>(test, HttpStatus.OK);
    }
    //    @ApiResponses(value = {
    //            @ApiResponse(responseCode = "200", description = "성공적인 조회")
    //    })
    @Operation(summary = "특정 쿠폰 조회")
    @GetMapping("/{couponId}")
    // 하나의 쿠폰 조회
    public ResponseEntity<CouponDto> coupon(
        @PathVariable(name = "couponId") Long id) {
        CouponDto couponDto = couponService.getCoupon(id);
        log.info("testCoupon={}", couponDto.toString());
        return new ResponseEntity<>(couponDto, HttpStatus.OK);
    }


}
