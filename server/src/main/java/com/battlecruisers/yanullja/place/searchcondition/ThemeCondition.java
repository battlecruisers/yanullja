package com.battlecruisers.yanullja.place.searchcondition;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.theme.ThemeType;
import com.battlecruisers.yanullja.theme.domain.Theme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ThemeCondition implements PlaceSearchCondition {

    @Override
    public List<Place> filterPlaces(List<Place> placeList,
        SearchConditionDto searchConditionDto) {
        List<ThemeType> themeTypeList = getThemeTypeList(
            searchConditionDto.getTheme());
        Map<Place, List<Theme>> placeThemeMap = placeList.stream()
            .collect(Collectors.toMap(place -> place, Place::getThemeList));
        return placeList.stream()
            .filter(place -> {
                List<ThemeType> placeThemeTypeList = placeThemeMap.get(place)
                    .stream()
                    .map(Theme::getType)
                    .toList();
                return placeThemeTypeList.containsAll(themeTypeList);
            })
            .collect(Collectors.toList());
    }

    private List<ThemeType> getThemeTypeList(String theme) {

        if (theme != null && !theme.isBlank()) {
            String[] themes = theme.split(",");

            return Arrays.stream(themes)
                .map(ThemeType::valueOf)
                .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
