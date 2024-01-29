package com.battlecruisers.yanullja.place;

import lombok.Getter;

@Getter
public enum PlaceCategory {

    HOTEL_RESORT("호텔_리조트"), PENSION_VILLA("펜션_풀빌라"), MOTEL("모텔");

    private final String name;

    PlaceCategory(String name) {
        this.name = name;
    }

    public static PlaceCategory fromString(String value) {
        for (PlaceCategory category : PlaceCategory.values()) {
            if (category.name.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException(
            "No constant with name " + value + " found in PlaceCategory enum");
    }
}
