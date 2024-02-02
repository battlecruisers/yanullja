package com.battlecruisers.yanullja.reservation.exception;

import lombok.Getter;

@Getter
public class NotEnoughTotalRoomCountException extends RuntimeException {

    public NotEnoughTotalRoomCountException() {
        super(ErrorCode.NOT_ENOUGH_TOTAL_ROOM_COUNT_EXCEPTION.getMessage());
    }
}
