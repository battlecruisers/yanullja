package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponResponseDto;
import com.battlecruisers.yanullja.coupon.exception.AlreadyRegisteredException;
import com.battlecruisers.yanullja.coupon.exception.AlreadyUsedException;
import com.battlecruisers.yanullja.coupon.exception.CouponUsageHistoryNotFoundException;
import com.battlecruisers.yanullja.coupon.exception.InvalidAccessException;
import com.battlecruisers.yanullja.coupon.exception.InvalidCouponException;
import com.battlecruisers.yanullja.coupon.exception.MemberCouponNotFoundException;
import com.battlecruisers.yanullja.coupon.exception.NoAvailableCouponsException;
import com.battlecruisers.yanullja.member.MemberRepository;
import com.battlecruisers.yanullja.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.battlecruisers.yanullja.coupon.domain.MemberCoupon.createMemberCoupon;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponService couponService;

    // 회원이 쿠폰 등록
    public void register(Long code, Long id) {

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
//        var member = memberRepository
//                .findById(1L)
//                .orElseThrow();

        var member = new Member(1L);

        // 쿠폰을 찾지 못했을 경우 예외처리
        var coupon = couponRepository
                .findById(code)
                .orElseThrow(() -> new MemberCouponNotFoundException(code));

        // 가져온 쿠폰이 유효하지 않은 쿠폰이거나 이미 등록된 쿠폰일 경우 예외 발생
        if (!coupon.getIsValid()) {
            throw new InvalidCouponException(coupon.getId());
        }

        if (coupon.getIsRegistered()) {
            throw new AlreadyRegisteredException(code);
        }

        MemberCoupon memberCoupon = createMemberCoupon(member, coupon, false);


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
        if (memberCoupon.getIsUsed()) {
            throw new AlreadyUsedException(memberCouponId);
        }

        // 멤버쿠폰의 isUsed 컬럼값을 true로 변경
        memberCoupon.updateUsageStatus();

        // DB에 반영
        memberCouponRepository.save(memberCoupon);

    }

    // 회원이 쿠폰을 적용한 할인가격을 구하는 함수
    public BigDecimal applyMemberCoupon(MemberCoupon memberCoupon, BigDecimal price) {
        // 회원쿠폰이 유효한 쿠폰인지 검증
        this.validateMemberCoupon(memberCoupon.getId(), 1L);

        // 객실가격이 쿠폰의 최소사용금액을 만족하는지 검증
        couponService.validateCoupon(memberCoupon.getCoupon(), price);

        // 할인이 적용된 가격 반환
        return couponService.calculateDiscountedPrice(memberCoupon.getCoupon(), price);

    }

    // 사용하려는 회원의 쿠폰이 유효한 쿠폰인지 검증
    public void validateMemberCoupon(Long memberCouponId, Long memberId) {
        MemberCoupon memberCoupon = memberCouponRepository.
                findById(memberId).orElseThrow(() -> new MemberCouponNotFoundException(memberCouponId));

        if (!(memberCoupon.getMember().getId() == memberId)) {
            throw new InvalidAccessException();
        }

    }


    // 회원이 사용한 쿠폰 내역 조회
    public List<MemberCouponDto> getUsageHistory(Long memberId) {

        var member = this.memberRepository.findById(2L).orElseThrow();

        var histories = memberCouponRepository.findByMemberAndIsUsed(member, true);

        // 리스트의 사이즈가 0일 경우 사용내역이 존재하지 않다는 예외 발생
        if (histories.size() == 0) {
            throw new CouponUsageHistoryNotFoundException(member.getId());
        }

        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();

        // Dto로 변환 후 반환
        for (MemberCoupon m : histories) {
            var dto = MemberCouponDto.from(m);
            memberCouponDtos.add(dto);
        }

        return memberCouponDtos;
    }


    // 회원이 특정 숙소에서 사용 가능한 쿠폰 목록 조회
    public List<MemberCouponDto> getRoomCoupons(Long roomId) {
        // Dto로 변환 후 반환
        List<MemberCoupon> roomCoupons = memberCouponRepository.findByRoomId(roomId);

        // 리스트의 사이즈가 0일 경우 사용할 수 있는 쿠폰이 없다는 예외를 발생
        if (roomCoupons.isEmpty()) {
            throw new NoAvailableCouponsException();
        }

        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();

        for (MemberCoupon m : roomCoupons) {
            var dto = MemberCouponDto.from(m);
            memberCouponDtos.add(dto);
        }

        return memberCouponDtos;
    }

    // 회원이 사용 가능한 쿠폰목록 조회
    public List<MemberCouponResponseDto> findMemberCouponsWithCoupon(Long memberId) {
//        fetch join this
//        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberAndIsUsed(memberId, false);
        // chat gpt change this to fetch join

        List<MemberCoupon> res = this.memberCouponRepository.findMemberCouponsWithCoupon(memberId);
        return res.stream().map(
                MemberCouponResponseDto::from
        ).collect(Collectors.toList());
    }
}
