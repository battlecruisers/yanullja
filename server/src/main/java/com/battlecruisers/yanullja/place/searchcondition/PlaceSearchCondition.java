package com.battlecruisers.yanullja.place.searchcondition;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import java.util.List;

public interface PlaceSearchCondition {

    List<Place> filterPlaces(List<Place> placeList,
        SearchConditionDto searchConditionDto);
}



