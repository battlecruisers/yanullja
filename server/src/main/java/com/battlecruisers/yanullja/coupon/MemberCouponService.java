package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.domain.Status;
import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
//    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    ;

    // 회원이 쿠폰 등록
    public void register(Long code, Long id){

        MemberCoupon memberCoupon = createMemberCoupon(code, id);

        memberCouponRepository.save(memberCoupon);

    }


    public MemberCoupon createMemberCoupon(Long code, Long memberId){
        MemberCoupon memberCoupon = new MemberCoupon();

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
        // var member = memberRepository.findById(memberId).orElseThrow()
        var coupon = couponRepository.findById(code).orElseThrow();
        Member member = new Member();
        member.setId(1L);



        // 회원쿠폰 정보 세팅(임시)
        memberCoupon.setCoupon(coupon);
        memberCoupon.setStatus(Status.Unused);
        memberCoupon.setMember(member);

        return memberCoupon;
}

}
