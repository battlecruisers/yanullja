package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.place.dto.PlaceListQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.domain.RoomImage;
import com.battlecruisers.yanullja.room.dto.RoomListQueryDto;
import com.battlecruisers.yanullja.theme.ThemeType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public List<PlaceListQueryDto> searchPlaces(String keyword,
        SearchConditionDto searchConditionDto) {

        String[] themes = searchConditionDto.getThemes().split(",");

        List<ThemeType> themeList = Arrays.stream(themes)
            .map(ThemeType::valueOf)
            .collect(Collectors.toList());

        SortType sortType = SortType.valueOf(searchConditionDto.getSort());

        List<Room> roomList = placeRepository.searchPlacesWithConditions(keyword,
            searchConditionDto, themeList, sortType);
        return null;
    }

    @Transactional(readOnly = true)
    public void queryPlace(Long placeId, LocalDate checkInDate, LocalDate checkOutDate) {

        List<RoomListQueryDto> roomListQueryDtoList = new ArrayList<>();
        List<Room> roomList = placeRepository.queryPlace(placeId);

        Long days = checkOutDate.toEpochDay() - checkInDate.toEpochDay();

        if (days <= 1) {
            roomListQueryDtoList = makePlaceDetailQueryDtoWithRent(checkInDate, roomList);
        } else {
            roomListQueryDtoList = makePlaceDetailQueryDtoWithoutRent(checkInDate, checkOutDate,
                roomList);
        }


    }

    private List<RoomListQueryDto> makePlaceDetailQueryDtoWithRent(LocalDate checkInDate,
        List<Room> roomList) {

        if (isWeekend(checkInDate)) {
            return roomList.stream()
                .map(room -> {
                    return new RoomListQueryDto(
                        room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(
                            Collectors.toList()),
                        room.getName(),
                        room.getCapacity(),
                        room.getWeekendRentStartTime(),
                        room.getWeekendRentEndTime(),
                        room.getWeekendRentTime(),
                        room.getWeekendRentPrice(),
                        room.getWeekendCheckInTime(),
                        room.getWeekendCheckOutTime(),
                        room.getWeekendStayPrice(),
                        null, null);
                })
                .collect(Collectors.toList());
        } else {
            return roomList.stream()
                .map(room -> {
                    return new RoomListQueryDto(
                        room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(
                            Collectors.toList()),
                        room.getName(),
                        room.getCapacity(),
                        room.getWeekdayRentStartTime(),
                        room.getWeekdayRentEndTime(),
                        room.getWeekdayRentTime(),
                        room.getWeekdayRentPrice(),
                        room.getWeekdayCheckInTime(),
                        room.getWeekdayCheckOutTime(),
                        room.getWeekdayStayPrice(),
                        null, null);
                })
                .collect(Collectors.toList());
        }
    }

    private List<RoomListQueryDto> makePlaceDetailQueryDtoWithoutRent(LocalDate checkInDate,
        LocalDate checkOutDate,
        List<Room> roomList) {

        Long days = (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
        Integer weekDayCount = getWeekDayCount(checkInDate, checkOutDate);
        Integer weekendCount = days.intValue() - weekDayCount;

        return roomList.stream()
            .map(room -> {
                return new RoomListQueryDto(
                    room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(
                        Collectors.toList()),
                    room.getName(),
                    room.getCapacity(),
                    null,
                    null,
                    null,
                    null,
                    weekDayCount >= 1 ? room.getWeekdayCheckInTime() : room.getWeekendCheckInTime(),
                    weekDayCount >= 1 ? room.getWeekdayCheckOutTime()
                        : room.getWeekendCheckOutTime(),
                    room.getWeekdayStayPrice() * weekDayCount
                        + room.getWeekendStayPrice() * weekendCount,
                    null, null);
            })
            .collect(Collectors.toList());


    }


    private Boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private Integer getWeekDayCount(LocalDate checkInDate,
        LocalDate checkOutDate) {

        Integer weekDayCount = 0;
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            if (!isWeekend(date)) {
                weekDayCount++;
            }
        }
        return weekDayCount;
    }


}
