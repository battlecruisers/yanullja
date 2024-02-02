package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.dto.CouponDto;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponDto;
import com.battlecruisers.yanullja.coupon.dto.MemberCouponResponseDto;
import com.battlecruisers.yanullja.coupon.exception.*;
import com.battlecruisers.yanullja.member.MemberRepository;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.battlecruisers.yanullja.coupon.domain.MemberCoupon.createMemberCoupon;
import static com.battlecruisers.yanullja.place.PlaceService.isWeekend;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponService couponService;

    public static BigDecimal getCalculateCouponDiscountAmount(
            BigDecimal originalPrice,
            BigDecimal discountPrice,
            BigDecimal discountRate,
            BigDecimal discountLimit) {
        // 할인금액
        BigDecimal discountAmount = BigDecimal.ZERO;

        // 할인율이 0일 경우 고정할인금액을, 고정할인금액이 0일 경우 할인율을 적용합니다.
        if (discountRate.compareTo(BigDecimal.ZERO) == 0) {
            discountAmount =
                    originalPrice.subtract(discountRate);
            // 할인금액 > 최대할인한도를 초과할경우 최대할인한도에 해당하는 금액을 반환합니다.
            if (discountAmount.compareTo(discountLimit) > 0) {
                discountAmount = discountLimit;
            }
        } else if (discountPrice.compareTo(BigDecimal.ZERO) == 0) {
            discountAmount =
                    originalPrice.multiply(BigDecimal.ONE.subtract(
                            discountRate.divide(BigDecimal.valueOf(100))));
            // 할인금액 > 최대할인한도를 초과할경우 최대할인한도에 해당하는 금액을 반환합니다.
            if (discountAmount.compareTo(discountLimit) > 0) {
                discountAmount = discountLimit;
            }
        }
        return discountAmount;

    }

    // 가장 할인을 많이 해주는 쿠폰 반환, 메서드에서 필요한 인자는 매개변수로 다 받아오게끔 처리
    public List<CouponDto> findMostDiscountedCoupon(Long roomId,
                                                    List<MemberCoupon> availableCoupons) {
        // 사용 가능한 쿠폰목록
//        List<MemberCoupon> availableCoupons = this.getAvailableCouponsByRoomId(roomId);

        // 최대 할인 쿠폰(최대로 할인 가능한 쿠폰이 2개 이상 존재할 수 있기 때문에 리스트를 사용)
        List<CouponDto> mostDiscountedCoupons = new ArrayList<>();

        // 최대로 할인 받는 금액을 초기화
        BigDecimal maxDiscountAmount = BigDecimal.ZERO;

        // 기준날짜 지정
        LocalDate nowTime = LocalDate.now();

        // 리스트에서 회원 쿠폰 정보를 읽어오면서 최대 할인 쿠폰 목록을 생성한다.
        for (MemberCoupon m : availableCoupons) {
            // 회원 쿠폰에서 필요한 정보를 추출
            Coupon c = m.getCoupon();
            // 필요한 방의 정보
            Room room = c.getRoom();
            // 숙박비용을 담을 변수를 초기화
            BigDecimal price = BigDecimal.ZERO;
            // 현재 날짜가 평일인지, 주말인지 구분해서 다른 가격을 적용합니다.
            if (isWeekend(nowTime)) {
                price = BigDecimal.valueOf(room.getWeekendStayPrice());
            } else {
                price = BigDecimal.valueOf(room.getWeekdayStayPrice());
            }

            // 객실의 숙박비용이 쿠폰 최소사용한도를 넘지 않을 경우 할인금액을 계산하지 않습니다.
            if (c.getMinimumPrice().compareTo(price) > 0) {
                continue;
            }

            BigDecimal discountAmount =
                    // 할인비용을 계산해주는 메서드를 호출합니다.
                    // 매개변수로 객실 숙박비용, 쿠폰의 고정할인금액, 쿠폰의 할인율, 쿠폰의 최대할인한도를 넘겨줍니다.
                    getCalculateCouponDiscountAmount(price, c.getDiscountPrice(),
                            BigDecimal.valueOf(c.getDiscountRate()),
                            c.getDiscountLimit());

            // 현재 순회중인 쿠폰의 할인금액 > maxDiscountAmount일 경우 리스트를 초기화합니다.
            // 순회중인 쿠폰을 리스트에 추가하고 maxDiscontAmount를 현재 순회중인 쿠폰의 할인금액으로 갱신합니다.
            if (discountAmount.compareTo(maxDiscountAmount) > 0) {
                mostDiscountedCoupons.clear();
                maxDiscountAmount = discountAmount;

                CouponDto couponDto = CouponDto.from(m.getCoupon());

                mostDiscountedCoupons.add(couponDto);
            } else if (discountAmount.compareTo(maxDiscountAmount) == 0) {
                CouponDto couponDto = CouponDto.from(m.getCoupon());
                // 현재 순회중인 쿠폰의 할인금액 == maxDiscountAmount일 경우 쿠폰을 리스트에 추가합니다.
                mostDiscountedCoupons.add(couponDto);
            }


        }

        return mostDiscountedCoupons;
    }

    // 특정 숙소에서 사용 가능한 쿠폰 목록 <<  MemberCouponService로 옮기는게 나을 듯
    public List<MemberCoupon> getAvailableCouponsByRoomId(Long roomId) {
        List<MemberCoupon> availableCoupons = memberCouponRepository.findByRoomId(
                roomId);
//        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();
//        for (MemberCoupon m : list) {
//            memberCouponDtos.add(MemberCouponDto.from(m));
//        }
//        return memberCouponDtos;
        return availableCoupons;
    }

    // 회원이 쿠폰 등록
    public void register(Long code, Long id) {

        // 로그인한 멤버정보와 등록할 쿠폰 정보를 DB에서 가져온다.
//        var member = memberRepository
//                .findById(1L)
//                .orElseThrow();

        Member member = new Member(1L);

        // 쿠폰을 찾지 못했을 경우 예외처리
        Coupon coupon = couponRepository
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
                .orElseThrow(
                        () -> new MemberCouponNotFoundException(memberCouponId));

        // 쿠폰의 사용여부를 체크한 후 사용했을 경우 예외처리
        if (memberCoupon.getIsUsed()) {
            throw new AlreadyUsedException(memberCouponId);
        }

        // 멤버쿠폰의 isUsed 컬럼값을 true로 변경
        memberCoupon.updateUsageStatus();

        // DB에 반영
        memberCouponRepository.save(memberCoupon);

    }

    // 회원이 쿠폰을 적용한 할인가격을 구하는 메서드
    public BigDecimal applyMemberCoupon(MemberCoupon memberCoupon,
                                        BigDecimal price) {
//        // 매개변수로 받아온 회원쿠폰이 유효한 쿠폰인지 검증
//        this.validateMemberCoupon(memberCoupon, 1L);

        // 회원쿠폰이 가지고 있는 쿠폰 정보를 추출
        Coupon coupon = memberCoupon.getCoupon();

//        // 객실가격이 쿠폰의 최소사용금액을 만족하는지 검증
//        couponService.
//            validateCoupon(coupon.getMinimumPrice(), price, coupon);

        // 쿠폰 적용결과를 계산해주는 메서드를 호출합니다.
        // 매개변수로 각각 숙박비용, 쿠폰의 고정할인금액, 쿠폰의 할인율, 쿠폰의 최대할인한도를 받습니다.
        return CouponService.
                getCalculateDiscountedPrice(price, coupon.getDiscountPrice(),
                        BigDecimal.valueOf(coupon.getDiscountRate())
                        , coupon.getDiscountLimit());
    }

    // 사용하려는 회원의 쿠폰이 유효한 쿠폰인지 검증
    public void validateMemberCoupon(MemberCoupon memberCoupon, Long memberId) {
        if (!(memberCoupon.getMember().getId().equals(memberId) && (
                memberCoupon.getIsUsed()))) {
            // 회원이 사용하려는 쿠폰과 접속중인 회원의 아이디가 일치하지 않거나, 이미 사용된 쿠폰일 경우 예외 발생
            throw new InvalidAccessException();
        }
    }

    // 회원이 사용한 쿠폰 내역 조회
    public List<MemberCouponDto> getUsageHistory(Long memberId) {

        Member member = this.memberRepository.findById(2L).orElseThrow();

        List<MemberCoupon> histories = memberCouponRepository.findByMemberAndIsUsed(
                member, true);

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
        List<MemberCoupon> roomCoupons = memberCouponRepository.findByRoomId(
                roomId);

        // 리스트의 사이즈가 0일 경우 사용할 수 있는 쿠폰이 없다는 예외를 발생
        if (roomCoupons.isEmpty()) {
            throw new NoAvailableCouponsException();
        }

//        List<MemberCouponDto> memberCouponDtos = new ArrayList<>();
//
//        for (MemberCoupon m : roomCoupons) {
//            var dto = MemberCouponDto.from(m);
//            memberCouponDtos.add(dto);
//        }

        return roomCoupons.stream().map(
                MemberCouponDto::from
        ).collect(Collectors.toList());

//        return memberCouponDtos;
    }

    // 회원이 사용 가능한 쿠폰목록 조회
    public List<MemberCouponResponseDto> findMemberCouponsWithCoupon(
            Long memberId) {

        List<MemberCoupon> res = this.memberCouponRepository.findMemberCouponsWithCoupon(
                memberId);
        return res.stream().map(
                MemberCouponResponseDto::from
        ).collect(Collectors.toList());
    }

}
