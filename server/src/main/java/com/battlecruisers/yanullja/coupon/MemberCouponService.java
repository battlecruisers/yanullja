package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.domain.Status;
import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
//    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository,
                               CouponRepository couponRepository){
        this.memberCouponRepository = memberCouponRepository;
        this.couponRepository = couponRepository;
    }

    // 회원이 쿠폰 등록
    public void register(Long code, HttpServletRequest request){

        MemberCoupon memberCoupon = createMemberCoupon(code, request);
        memberCouponRepository.save(memberCoupon);

    }

    public MemberCoupon createMemberCoupon(Long code, HttpServletRequest request){
        MemberCoupon memberCoupon = new MemberCoupon();
        // 세션으로부터 회원 아이디를 조회해온다.
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("id");

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
        // Member member = MemberRepository.findById(memberId);
        Optional<Coupon> coupon = couponRepository.findById(code);

        // 임시로 멤버 정보 생성
        Member member = new Member();
        member.setId(1l);

        // 회원쿠폰 정보 세팅
        memberCoupon.setCoupon(coupon.get());
        memberCoupon.setStatus(Status.Unused);
        memberCoupon.setMember(member);

        return memberCoupon;
    }

}
