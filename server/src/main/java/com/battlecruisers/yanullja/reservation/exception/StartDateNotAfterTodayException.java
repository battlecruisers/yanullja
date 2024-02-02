package com.battlecruisers.yanullja.reservation.exception;

import lombok.Getter;

@Getter
public class StartDateNotAfterTodayException extends RuntimeException {

    public StartDateNotAfterTodayException() {
        super(ErrorCode.START_DATE_NOT_AFTER_TODAY_EXCEPTION.getMessage());
    }

}
