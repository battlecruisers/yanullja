package com.battlecruisers.yanullja.purchase;

import com.battlecruisers.yanullja.coupon.MemberCouponRepository;
import com.battlecruisers.yanullja.coupon.MemberCouponService;
import com.battlecruisers.yanullja.coupon.domain.MemberCoupon;
import com.battlecruisers.yanullja.coupon.exception.MemberCouponNotFoundException;
import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final MemberCouponService memberCouponService;
    private final MemberCouponRepository memberCouponRepository;

    /**
     * 예약 정보를 기반으로 결제를 진행합니다.
     *
     * @param reservation 예약 정보
     * @return purchase 결제 엔티티
     */
    public Purchase purchase(Reservation reservation, Long memberCouponId) {
        // 금액 계산
        Room room = reservation.getRoom();
        BigDecimal totalPrice = room.calcTotalPrice(reservation.getStartDate(),
                reservation.getEndDate());

        // 쿠폰 적용 (사용 가능 여부 판단 &  쿠폰 적용 후 금액 계산)
        BigDecimal finalPrice = totalPrice;

        if (memberCouponId != null) {
            MemberCoupon memberCoupon = validateAndGetMemberCoupon(
                    memberCouponId);
            finalPrice = memberCouponService.applyMemberCoupon(memberCoupon,
                    totalPrice);

            // 쿠폰 사용상태:isUsed 변경 (미사용 -> 사용)
            memberCoupon.updateUsageStatus();
            memberCouponRepository.save(memberCoupon);
        }

        log.info("finalPrice = {}", finalPrice);

        // 결제 정보 저장
        Purchase purchase = Purchase.createPurchase(reservation, finalPrice);
        purchaseRepository.save(purchase);

        return purchase;
    }

    private MemberCoupon validateAndGetMemberCoupon(Long memberCouponId) {
//        return memberCouponRepository.
//            findById(memberCouponId).orElseThrow(
//                () -> new MemberCouponNotFoundException(memberCouponId));

        return memberCouponRepository.queryMemberCoupon(memberCouponId)
                .orElseThrow(() -> new MemberCouponNotFoundException(memberCouponId));
    }

}
