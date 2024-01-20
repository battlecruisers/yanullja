package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.ThemeType;
import com.battlecruisers.yanullja.theme.domain.Theme;
import java.time.LocalDate;
import java.util.List;
import org.springframework.core.annotation.MergedAnnotations.Search;

public interface CustomPlaceRepository {

    public List<Room> searchPlacesWithConditions(String keyword,
        SearchConditionDto searchConditionDto, List<ThemeType> themeList,
        SortType sortType);
}
