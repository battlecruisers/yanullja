package com.battlecruisers.yanullja.place;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {

    PRICE_LOW("예약가 낮은 순"),
    PRICE_HIGH("예약가 높은 순"),
    REVIEW_GOOD("후기 좋은 순"),
    REVIEW_MANY("후기 많은 순"),
    BOOKMARK_MANY("찜 많은 순"),
    ;

    private String name;
}
