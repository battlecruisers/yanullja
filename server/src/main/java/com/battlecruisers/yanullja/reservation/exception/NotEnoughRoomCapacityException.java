package com.battlecruisers.yanullja.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotEnoughRoomCapacityException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.NOT_ENOUGH_ROOM_CAPACITY_EXCEPTION;
}
