package com.battlecruisers.yanullja.coupon;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/membercoupons")
public class MemberCouponController {
    private final MemberCouponService memberCouponService;

    public MemberCouponController(MemberCouponService memberCouponService){
        this.memberCouponService = memberCouponService;
    }
    @PostMapping("/{code}")
    // 회원이 쿠폰 등록
    public void register(@PathVariable(name = "code") Long code, HttpServletRequest request){
        memberCouponService.register(code, request);
    }

    // 회원이 보유한 최대할인쿠폰정보를 받아오려면 가격 정보도 필요
    // 상품가격에 따라 %할인이 더 많이 할인될 수 있고, 1000~10000 단위 고정값 할인이 더 할인 받을 수 있기 때문에.
}
