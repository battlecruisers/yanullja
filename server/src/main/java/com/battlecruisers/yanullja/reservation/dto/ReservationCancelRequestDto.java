package com.battlecruisers.yanullja.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationCancelRequestDto {

    @NotNull
    private Long paymentId; // reservationId
}
