package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.ThemeType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<PlaceQueryDto> searchPlaces(String keyword, SearchConditionDto searchConditionDto) {

        String[] themes = searchConditionDto.getThemes().split(",");

        List<ThemeType> themeList = Arrays.stream(themes)
            .map(ThemeType::valueOf)
            .collect(Collectors.toList());

        SortType sortType = SortType.valueOf(searchConditionDto.getSort());

        List<Room> roomList = placeRepository.searchPlacesWithConditions(keyword,
            searchConditionDto, themeList, sortType);
        return null;
    }
}