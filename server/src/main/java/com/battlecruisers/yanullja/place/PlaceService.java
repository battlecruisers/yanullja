package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.place.dto.PlaceQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.domain.RoomImage;
import com.battlecruisers.yanullja.room.dto.RoomListQueryDto;
import com.battlecruisers.yanullja.theme.ThemeType;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    public List<PlaceQueryDto> searchPlaces(String keyword,
        SearchConditionDto searchConditionDto) {

        String[] themes = searchConditionDto.getThemes().split(",");

        List<ThemeType> themeList = Arrays.stream(themes)
            .map(ThemeType::valueOf)
            .collect(Collectors.toList());

        SortType sortType = SortType.valueOf(searchConditionDto.getSort());

        List<Room> roomList = placeRepository.searchPlacesWithConditions(keyword,
            searchConditionDto, themeList, sortType);

        roomListToPlaceListQueryDtoList(roomList);
        return null;
    }

    private void roomListToPlaceListQueryDtoList(List<Room> roomList) {

    }

    @Transactional(readOnly = true)
    public List<RoomListQueryDto> queryPlace(Long placeId, LocalDate checkInDate,
        LocalDate checkOutDate) {

        List<Room> roomList = placeRepository.queryPlace(placeId);

        return getRoomListQueryDto(checkInDate, checkOutDate, roomList);
    }

    private List<RoomListQueryDto> getRoomListQueryDto(LocalDate checkInDate,
        LocalDate checkOutDate, List<Room> roomList) {

        Long days = checkOutDate.toEpochDay() - checkInDate.toEpochDay();

        if (days <= 1) {
            return makePlaceDetailQueryDtoWithRent(checkInDate, roomList);
        } else {
            return makePlaceDetailQueryDtoWithoutRent(checkInDate, checkOutDate, roomList);
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
                        findMaxDiscountPrice(room, checkInDate, RoomType.DayUse),
                        findMaxDiscountPrice(room, checkInDate, RoomType.Stay));
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
                        findMaxDiscountPrice(room, checkInDate, RoomType.DayUse),
                        findMaxDiscountPrice(room, checkInDate, RoomType.Stay));
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
                    null, findMaxDiscountPrice(room, checkInDate, RoomType.Stay));
            })
            .collect(Collectors.toList());


    }

    private Integer findMaxDiscountPrice(Room room, LocalDate checkInDate, RoomType roomType) {
        List<Coupon> couponList = room.getCoupons();

        return couponList.stream()
            .filter(coupon -> (coupon.getValidityStartDate().isBefore(checkInDate)
                && coupon.getValidityEndDate().isAfter(checkInDate)
                && coupon.getIsValid()
                && (coupon.getRoomType().equals(roomType)
                || coupon.getRoomType().equals(RoomType.All)
            )))
            .mapToInt(coupon -> coupon.getDiscountPrice().intValue())
            .max().orElseGet(() -> {
                return 0;
            });
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
