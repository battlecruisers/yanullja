package com.battlecruisers.yanullja.reservation.exception;

import lombok.Getter;

@Getter
public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND_EXCEPTION.getMessage());
    }

}
