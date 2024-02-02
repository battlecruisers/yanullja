package com.battlecruisers.yanullja.coupon.domain;

import lombok.Getter;

@Getter
public enum RoomType {
    STAY("STAY"), RENT("RENT"), ALL("ALL");

    private final String name;

    RoomType(String name) {
        this.name = name;
    }
}
