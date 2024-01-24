package com.battlecruisers.yanullja.coupon.domain;

import lombok.Getter;

@Getter
public enum RoomType {
    Stay("Stay"), DayUse("DayUse"), All("All");

    private String name;

    RoomType(String name){
        this.name = name;
    }
}
