package com.battlecruisers.yanullja.reservation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequestDto {

    private Long memberId;
    private Long roomId;
    private LocalDate startDate;
    private LocalDate endDate;

}
