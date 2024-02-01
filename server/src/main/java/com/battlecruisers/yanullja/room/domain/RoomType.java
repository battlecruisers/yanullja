package com.battlecruisers.yanullja.room.domain;

public enum RoomType {

    STAY("숙박"), RENT("대실");

    String name;

    RoomType(String name) {
        this.name = name;
    }
}
