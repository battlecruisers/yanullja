package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CustomPlaceRepository {

    List<Place> searchPlacesWithConditions(
        SearchConditionDto searchConditionDto);

    List<Room> queryPlace(Long placeId, LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer guestCount);

    List<Place> queryPlacesInRegion(String regionName, Pageable pageable);

    List<Place> queryPlaceInCategory(String categoryName,
        PlaceCategory placeCategory);

    List<Place> queryPlacesRanking(Pageable pageable);
}
