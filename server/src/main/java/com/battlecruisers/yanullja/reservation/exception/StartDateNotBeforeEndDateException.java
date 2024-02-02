package com.battlecruisers.yanullja.reservation.exception;

import lombok.Getter;

@Getter
public class StartDateNotBeforeEndDateException extends RuntimeException {

    public StartDateNotBeforeEndDateException() {
        super(ErrorCode.START_DATE_NOT_BEFORE_END_DATE_EXCEPTION.getMessage());
    }
}
