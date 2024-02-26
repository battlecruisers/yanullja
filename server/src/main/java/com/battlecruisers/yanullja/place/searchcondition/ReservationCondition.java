package com.battlecruisers.yanullja.place.searchcondition;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.reservation.ReservationRepository;
import com.battlecruisers.yanullja.reservation.ReservationService;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.room.domain.Room;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationCondition implements PlaceSearchCondition {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @Override
    public List<Place> filterPlaces(List<Place> placeList,
        SearchConditionDto searchConditionDto) {
        LocalDate checkInDate = searchConditionDto.getStartDate();
        LocalDate checkOutDate = searchConditionDto.getEndDate();

        Map<Room, List<Reservation>> roomReservationMap = getRoomReservationMap(
            placeList, checkInDate, checkOutDate);

        Map<Room, Integer> maxAvailableRoomCountMap = getMaxAvailableRoomCountMap(
            roomReservationMap, checkInDate, checkOutDate);

        return placeList.stream()
            .filter(place -> {
                List<Room> roomList = place.getRoomList();
                for (Room room : roomList) {
                    Integer maxAvailableRoomCount = maxAvailableRoomCountMap.get(
                        room);
                    if (maxAvailableRoomCount != null
                        && maxAvailableRoomCount > 0) {
                        return true;
                    }
                }
                return false;
            })
            .collect(Collectors.toList());
    }

    private Map<Room, Integer> getMaxAvailableRoomCountMap(
        Map<Room, List<Reservation>> roomReservationMap, LocalDate checkInDate,
        LocalDate checkOutDate) {
        Map<Room, Integer> maxAvailableRoomCountMap = new HashMap<>();
        for (Room room : roomReservationMap.keySet()) {
            Integer maxAvailableRoomCount = reservationService.getMaxAvailableRoomCount(
                roomReservationMap.get(room), room,
                checkInDate, checkOutDate);
            maxAvailableRoomCountMap.put(room, maxAvailableRoomCount);
        }
        return maxAvailableRoomCountMap;
    }

    private Map<Room, List<Reservation>> getRoomReservationMap(
        List<Place> placeList,
        LocalDate checkInDate, LocalDate checkOutDate) {
        Map<Room, List<Reservation>> roomReservationMap
            = reservationRepository.queryReservationsInDateRange(placeList,
                checkInDate, checkOutDate)
            .stream()
            .collect(Collectors.groupingBy(Reservation::getRoom));
        return roomReservationMap;
    }
}
