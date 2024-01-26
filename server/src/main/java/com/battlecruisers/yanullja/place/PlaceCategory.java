package com.battlecruisers.yanullja.place;

import lombok.Getter;

@Getter
public enum PlaceCategory {

    HOTEL_RESORT("호텔_리조트"), PENSION_VILLA("펜션_풀빌라"), MOTEL("모텔");

    private final String name;

    PlaceCategory(String name) {
        this.name = name;
    }
}
