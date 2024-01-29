package com.battlecruisers.yanullja.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StartDateNotAfterTodayException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.START_DATE_NOT_AFTER_TODAY_EXCEPTION;
}
