package com.battlecruisers.yanullja.theme;

import lombok.Getter;

@Getter
public enum ThemeType {

    PC("객실내PC"), POOL("수영장"), PET("애견동반"), BARBEQUE("바베큐"), BREAKFAST(
        "조식"), KIDS("키즈"),
    PARKING("주차가능"), OTT("OTT무료"), PARTY_ROOM("파티룸"), MIRROR("거울룸"), KARAOKE(
        "노래방"), GAME("게임"),
    WIFI("와이파이"), MEDICINE("약"), BATH("욕조"), KITCHEN("주방");

    private final String name;

    ThemeType(String name) {
        this.name = name;
    }

}
