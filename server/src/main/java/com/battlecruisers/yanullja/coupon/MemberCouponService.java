package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.coupon.exception.AlreadyRegisteredException;
import com.battlecruisers.yanullja.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

        createMemberCoupon(code, id);

    }


    public void createMemberCoupon(Long code, Long memberId) {
        MemberCoupon memberCoupon = new MemberCoupon();

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
        // var member = memberRepository.findById(memberId).orElseThrow()
        var coupon = couponRepository.findById(code).orElseThrow();
        var member = memberRepository.findById(Long.valueOf(1L)).orElseThrow();

        // 회원쿠폰 정보 세팅(임시)
        memberCoupon.setCoupon(coupon);
        memberCoupon.setUsed(true);
        memberCoupon.setMember(member);

        try {
            memberCouponRepository.save(memberCoupon);
        } catch (DataIntegrityViolationException d) {
            System.out.println("예외 던지기");
            throw new AlreadyRegisteredException(code);
        }


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
