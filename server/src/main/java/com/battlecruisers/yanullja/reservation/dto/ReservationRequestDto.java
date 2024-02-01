package com.battlecruisers.yanullja.reservation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequestDto {

    private Long roomOptionId; // O (roomOptionId).
    private Long memberCouponId; //
    private LocalDate reservationStartDate; // = reservationStartDate
    private LocalDate reservationEndDate; // = reservationEndDate

}
