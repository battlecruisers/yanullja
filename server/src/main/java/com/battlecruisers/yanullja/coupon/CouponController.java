package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.coupon.dto.CouponDto;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public ResponseEntity<List<CouponDto>> list() {
        List<CouponDto> test = couponService.getCouponList();
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @GetMapping("/{couponId}")
    // 하나의 쿠폰 조회

    public ResponseEntity<CouponDto> coupon(@PathVariable(name = "couponId") Long id) {
        CouponDto couponDto = couponService.getCoupon(id);
        log.info("testCoupon={}", couponDto.toString());
        return new ResponseEntity<>(couponDto, HttpStatus.OK);
    }


    // 쿠폰 생성 테스트
    @PostMapping
    public ResponseEntity<Long> insert() {
        Long couponId = couponService.createCoupon();
        return new ResponseEntity<>(couponId, HttpStatus.OK);
    }


    // 최대 할인 쿠폰 조회
    @GetMapping("/rooms/{roomId}/max-discount-coupons")
    public ResponseEntity<List<CouponDto>> maxCouponDtos(@PathVariable(name = "roomId") Long roomId) {

        Room room = couponService.getRoomInfo(roomId);

        List<MemberCouponDto> list = couponService.getAvailableCouponsByRoomId(room.getId());

        List<CouponDto> maxDiscountCoupons = couponService.findMostDiscountedCoupon(list, room, LocalDate.now(), RoomType.DayUse);

        return ResponseEntity.ok().body(maxDiscountCoupons);

    }
}
