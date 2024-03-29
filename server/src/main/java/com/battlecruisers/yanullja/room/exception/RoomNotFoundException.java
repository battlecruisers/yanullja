package com.battlecruisers.yanullja.room.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(Long id) {
        super("Could not find room " + id);
    }
}
