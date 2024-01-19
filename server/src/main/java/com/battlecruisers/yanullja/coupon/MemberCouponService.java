package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.member.MemberRepository;
import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    // 회원이 쿠폰 등록
    public void register(Long code, Long id) {

        MemberCoupon memberCoupon = createMemberCoupon(code, id);

        memberCouponRepository.save(memberCoupon);

    }


    public MemberCoupon createMemberCoupon(Long code, Long memberId) {
        MemberCoupon memberCoupon = new MemberCoupon();

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
        // var member = memberRepository.findById(memberId).orElseThrow()
        var coupon = couponRepository.findById(code).orElseThrow();
        Member member = new Member();
        member.setId(1L);


        // 회원쿠폰 정보 세팅(임시)
        memberCoupon.setCoupon(coupon);
        memberCoupon.setUsed(true);
        memberCoupon.setMember(member);

        return memberCoupon;
    }

    // 회원이 사용한 쿠폰 내역 조회
    public List<MemberCouponDto> getUsageHistory(Long memberId) {

        var me = this.memberRepository.findById(1L).orElseThrow();

        var histories = memberCouponRepository.findByMemberAndIsUsed(me, true)
                .orElseThrow();

        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();

        // Dto로 변환 후 반환
        for (MemberCoupon m : histories) {
            MemberCouponDto dto = new MemberCouponDto();
            dto.setId(m.getId());
            dto.setMemberId(m.getMember().getId());
            dto.setCouponId(m.getCoupon().getId());
            dto.setUsed(m.isUsed());

            memberCouponDtos.add(dto);
        }

        return memberCouponDtos;
    }

}
