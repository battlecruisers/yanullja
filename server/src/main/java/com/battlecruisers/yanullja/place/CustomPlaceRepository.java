package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.ThemeType;
import java.util.List;

public interface CustomPlaceRepository {

    List<Room> searchPlacesWithConditions(String keyword,
        SearchConditionDto searchConditionDto, List<ThemeType> themeList,
        SortType sortType);

    List<Room> queryPlace(Long placeId);
}
