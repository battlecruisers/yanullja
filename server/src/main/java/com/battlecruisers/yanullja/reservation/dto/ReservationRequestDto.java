package com.battlecruisers.yanullja.reservation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequestDto {

    private Long roomOptionId; // O (roomOptionId).
    private Long memberCouponId; // 대현님 React 추가하셔야 하는 부분. coupon 대신 memberCoupon 사용하는 방향으로
    private LocalDate reservationStartDate; // = reservationStartDate
    private LocalDate reservationEndDate; // = reservationEndDate

}
