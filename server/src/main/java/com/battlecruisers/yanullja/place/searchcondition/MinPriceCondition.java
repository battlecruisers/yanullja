package com.battlecruisers.yanullja.place.searchcondition;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MinPriceCondition implements PlaceSearchCondition {

    @Override
    public List<Place> filterPlaces(List<Place> placeList,
        SearchConditionDto searchConditionDto) {
        LocalDate checkInDate = searchConditionDto.getStartDate();
        LocalDate checkOutDate = searchConditionDto.getEndDate();
        Integer minPrice = searchConditionDto.getMinPrice();

        return placeList.stream()
            .filter(place -> {
                return place.getMinimumPrice(checkInDate, checkOutDate)
                    >= minPrice;
            })
            .collect(Collectors.toList());
    }
}
