package com.battlecruisers.yanullja.roominfo.domain;

import lombok.Getter;

@Getter
public enum RentalType {

    RENT("대실"), STAY("숙박");

    private String name;

    RentalType(String name) {
        this.name = name;
    }
}
