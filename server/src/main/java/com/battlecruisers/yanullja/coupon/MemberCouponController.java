package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.dto.CouponRequestDto;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member-coupons")
@RequiredArgsConstructor
@Slf4j
public class MemberCouponController {
    private final MemberCouponService memberCouponService;

    @PostMapping("")
    // 회원이 쿠폰 등록
    public void register(@RequestBody CouponRequestDto dto, HttpServletRequest request) {
        // 세션에서 회원 아이디 추출
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("id");

        memberCouponService.register(dto.getCode(), memberId);
    }

    // 회원이 보유한 최대할인쿠폰정보를 받아오려면 가격 정보도 필요
    // 상품가격에 따라 %할인이 더 많이 할인될 수 있고, 1000~10000 단위 고정값 할인이 더 할인 받을 수 있기 때문에


    // 회원이 사용한 쿠폰 내역 조회
    @GetMapping("/usage-history")
    public List<MemberCouponDto> history(HttpServletRequest request) {
        // 세션에서 회원 아이디 추출
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("id");

        // 사용내역 반환
        List<MemberCouponDto> histories = memberCouponService.getUsageHistory(memberId);

        return histories;
    }

    // 회원이 쿠폰 사용하는 과정 테스트
    @PatchMapping("/{memberCouponId}")
    public void use(@PathVariable(name="memberCouponId") Long memberCouponId){
        // 쿠폰 사용 테스트
        memberCouponService.updateStatus(memberCouponId);
    }

    // 특정 숙소에서 사용 가능한 쿠폰 조회
    @GetMapping("/{roomId}")
    public List<MemberCouponDto> room(@RequestParam(defaultValue = "0", name = "page")int page,
                                      @RequestParam(defaultValue = "10", name = "size")int size,
                                      @PathVariable(name="roomId")Long roomId){
        Pageable pageable = PageRequest.of(page, size);
        List<MemberCouponDto> memberCouponDtos = memberCouponService.getRoomCoupon(roomId, pageable);
        return memberCouponDtos;
    }
}
