package com.battlecruisers.yanullja.place.searchcondition;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CapacityCondition implements PlaceSearchCondition {

    @Override
    public List<Place> filterPlaces(List<Place> placeList,
        SearchConditionDto searchConditionDto) {

        return placeList.stream()
            .filter(place -> {
                boolean flag = false;
                List<Room> roomList = place.getRoomList();
                for (Room room : roomList) {
                    if (room.getCapacity() >= searchConditionDto.getGuest()) {
                        flag = true;
                        break;
                    }
                }
                return flag;
            })
            .collect(Collectors.toList());
    }
}
