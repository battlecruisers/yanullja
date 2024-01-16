package com.battlecruisers.yanullja.roominfo.domain;

import lombok.Getter;

@Getter
public enum UsageDayType {
    WEEKDAY("주중"), WEEKEND("주말");

    private String name;

    UsageDayType(String name) {
        this.name = name;
    }
}
