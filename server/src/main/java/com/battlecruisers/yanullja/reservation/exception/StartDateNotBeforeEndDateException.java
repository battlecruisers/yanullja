package com.battlecruisers.yanullja.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StartDateNotBeforeEndDateException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.START_DATE_NOT_BEFORE_END_DATE_EXCEPTION;
}
