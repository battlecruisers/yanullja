package com.battlecruisers.yanullja.reservation.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ReservationRequestDto {

    private Long roomOptionId; // O (roomOptionId).
    private Long memberCouponId; //
    private LocalDate reservationStartDate; // = reservationStartDate
    private LocalDate reservationEndDate; // = reservationEndDate

}
