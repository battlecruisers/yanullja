package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.coupon.exception.AlreadyRegisteredException;
import com.battlecruisers.yanullja.coupon.exception.AlreadyUsedException;
import com.battlecruisers.yanullja.coupon.exception.CouponUsageHistoryNotFoundException;
import com.battlecruisers.yanullja.coupon.exception.InvalidCouponException;
import com.battlecruisers.yanullja.coupon.exception.MemberCouponNotFoundException;
import com.battlecruisers.yanullja.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    // 쿠폰 정보 생성
    public void createMemberCoupon(Long code, Long memberId) {

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
        var member = memberRepository
                .findById(1L)
                .orElseThrow();

        // 쿠폰을 찾지 못했을 경우 예외처리
        var coupon = couponRepository
                .findById(code)
                .orElseThrow(() -> new MemberCouponNotFoundException(code));

        // 가져온 쿠폰이 유효하지 않은 쿠폰이거나 이미 등록된 쿠폰일 경우 예외 발생
        if (!coupon.isValid()) {
            throw new InvalidCouponException(coupon.getId());
        } else if (coupon.isRegistered()) {
            throw new AlreadyRegisteredException(code);
        }

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .isUsed(false)
                .build();


        // 회원쿠폰 정보 세팅(임시)


        memberCouponRepository.save(memberCoupon);

        // 회원이 사용한 쿠폰의 등록여부를 변경해준다.
        coupon.changeRegistrationStatus();
        couponRepository.save(coupon);
    }

    // 회원이 쿠폰을 사용 -> 쿠폰의 상태를 변경
    public void updateStatus(Long memberCouponId) {
        // 멤버쿠폰 정보를 조회해온다.

        MemberCoupon memberCoupon = memberCouponRepository
                .findById(memberCouponId)
                .orElseThrow(() -> new MemberCouponNotFoundException(memberCouponId));

        // 쿠폰의 사용여부를 체크한 후 사용했을 경우 예외처리
        if (!memberCoupon.isUsed()) {
            // 멤버쿠폰의 isUsed 컬럼값을 true로 변경
            memberCoupon.updateUsageStatus();
        } else {
            throw new AlreadyUsedException(memberCouponId);
        }

        // DB에 반영
        memberCouponRepository.save(memberCoupon);

    }


    // 회원이 사용한 쿠폰 내역 조회
    public List<MemberCouponDto> getUsageHistory(Long memberId) throws CouponUsageHistoryNotFoundException {

        var member = this.memberRepository.findById(2L).orElseThrow();

        var histories = memberCouponRepository.findByMemberAndIsUsed(member, true);

        // 리스트의 사이즈가 0일 경우 사용내역이 존재하지 않다는 예외 발생
        if (histories.size() == 0)
            throw new CouponUsageHistoryNotFoundException(member.getId());

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


    // 회원이 특정 숙소에서 사용 가능한 쿠폰 목록 조회
    public List<MemberCouponDto> getRoomCoupon(Long roomId, Pageable pageable) {
        var RoomCoupons = memberCouponRepository.findByRoomId(roomId, pageable).orElseThrow();
        // Dto로 변환 후 반환

        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();

        for (MemberCoupon m : RoomCoupons) {
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
