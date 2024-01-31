package com.battlecruisers.yanullja.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationNotFoundException {

    private final ErrorCode errorCode = ErrorCode.RESERVATION_NOT_FOUND_EXCEPTION;

}
