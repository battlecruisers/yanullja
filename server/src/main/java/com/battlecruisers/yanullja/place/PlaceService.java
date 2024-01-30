package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.PlaceInfoQueryDto;
import com.battlecruisers.yanullja.place.dto.PlaceQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.place.dto.SearchResponseDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.dto.RoomQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public static Boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static Integer getWeekDayCount(LocalDate checkInDate,
                                          LocalDate checkOutDate) {

        Integer weekDayCount = 0;
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            if (!isWeekend(date)) {
                weekDayCount++;
            }
        }
        return weekDayCount;
    }

    public static Integer findMaxDiscountPrice(Room room, LocalDate checkInDate,
                                               RoomType roomType) {
        List<Coupon> couponList = room.getCoupons();
//        MemeberCoupon memberCoupon = findMaxDiscountPrice(room, MemberCouponList)

        return couponList.stream()
                .filter(coupon -> (coupon.getValidityStartDate().isBefore(checkInDate)
                        && coupon.getValidityEndDate().isAfter(checkInDate)
                        && coupon.getIsValid()
                        && (coupon.getRoomType().equals(roomType)
                        || coupon.getRoomType().equals(RoomType.ALL)
                )))
                .mapToInt(coupon -> coupon.getDiscountPrice().intValue())
                .max().orElseGet(() -> {
                    return 0;
                });
    }

    @Transactional(readOnly = true)
    public SearchResponseDto searchPlaces(SearchConditionDto searchConditionDto) {
        List<Place> placeList = placeRepository.searchPlacesWithConditions(
                searchConditionDto, null, null);

        return roomListToPlaceListQueryDtoList(placeList, searchConditionDto.getStartDate(),
                searchConditionDto.getEndDate());

        /**
         * TODO : 나중에 테마리스트, 정렬 프론트엔드 기능 추가시 추가할예정
         */
//        String[] themes = searchConditionDto.getThemes().split(",");
//
//        List<ThemeType> themeList = Arrays.stream(themes)
//            .map(ThemeType::valueOf)
//            .collect(Collectors.toList());
//
//        SortType sortType = SortType.valueOf(searchConditionDto.getSort());

    }

    /**
     * TODO : 지금은 숙박만 진행하지만 대실도 진행하는 경우 추가할 예정
     */
//    private List<RoomListQueryDto> makePlaceDetailQueryDtoWithRent(LocalDate checkInDate,
//        List<Room> roomList) {
//
//        if (isWeekend(checkInDate)) {
//            return roomList.stream()
//                .map(room -> {
//                    return new RoomListQueryDto(
//                        room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(
//                            Collectors.toList()),
//                        room.getName(),
//                        room.getCapacity(),
//                        room.getWeekendRentStartTime(),
//                        room.getWeekendRentEndTime(),
//                        room.getWeekendRentTime(),
//                        room.getWeekendRentPrice(),
//                        room.getWeekendCheckInTime(),
//                        room.getWeekendCheckOutTime(),
//                        room.getWeekendStayPrice(),
//                        findMaxDiscountPrice(room, checkInDate, RoomType.DayUse),
//                        findMaxDiscountPrice(room, checkInDate, RoomType.Stay));
//                })
//                .collect(Collectors.toList());
//        } else {
//            return roomList.stream()
//                .map(room -> {
//                    return new RoomListQueryDto(
//                        room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(
//                            Collectors.toList()),
//                        room.getName(),
//                        room.getCapacity(),
//                        room.getWeekdayRentStartTime(),
//                        room.getWeekdayRentEndTime(),
//                        room.getWeekdayRentTime(),
//                        room.getWeekdayRentPrice(),
//                        room.getWeekdayCheckInTime(),
//                        room.getWeekdayCheckOutTime(),
//                        room.getWeekdayStayPrice(),
//                        findMaxDiscountPrice(room, checkInDate, RoomType.DayUse),
//                        findMaxDiscountPrice(room, checkInDate, RoomType.Stay));
//                })
//                .collect(Collectors.toList());
//        }
//    }
//
//
//    private List<RoomListQueryDto> makePlaceDetailQueryDtoWithoutRent(LocalDate checkInDate,
//        LocalDate checkOutDate, List<Room> roomList) {
//
//        Long days = (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
//        Integer weekDayCount = getWeekDayCount(checkInDate, checkOutDate);
//        Integer weekendCount = days.intValue() - weekDayCount;
//
//        return roomList.stream()
//            .map(room -> {
//                return new RoomListQueryDto(
//                    room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(
//                        Collectors.toList()),
//                    room.getName(),
//                    room.getCapacity(),
//                    null,
//                    null,
//                    null,
//                    null,
//                    weekDayCount >= 1 ? room.getWeekdayCheckInTime() : room.getWeekendCheckInTime(),
//                    weekDayCount >= 1 ? room.getWeekdayCheckOutTime()
//                        : room.getWeekendCheckOutTime(),
//                    room.getWeekdayStayPrice() * weekDayCount
//                        + room.getWeekendStayPrice() * weekendCount,
//                    null, findMaxDiscountPrice(room, checkInDate, RoomType.Stay));
//
//
//            })
//            .collect(Collectors.toList());
//
//
//    }
    private SearchResponseDto roomListToPlaceListQueryDtoList(List<Place> placeList,
                                                              LocalDate checkInDate, LocalDate checkOutDate) {
        List<PlaceQueryDto> placeQueryDtoList = placeList.stream()
                .map(place -> {
                    return PlaceQueryDto.from(place, checkInDate, checkOutDate);
                })
                .collect(Collectors.toList());
        return new SearchResponseDto(placeQueryDtoList);
    }

    @Transactional(readOnly = true)
    public PlaceInfoQueryDto queryPlace(Long placeId, LocalDate checkInDate,
                                        LocalDate checkOutDate, Integer guestCount) {

        List<Room> roomList
                = placeRepository.queryPlace(placeId, checkInDate, checkOutDate, guestCount);

        return getPlaceRoomInfoQueryDto(placeId, checkInDate, checkOutDate, roomList);
    }

    private PlaceInfoQueryDto getPlaceRoomInfoQueryDto(Long placeId, LocalDate checkInDate,
                                                       LocalDate checkOutDate, List<Room> roomList) {

        //TODO : 대실이 추가되면 추가할 예정
//        if (days <= 1L) {
//            return makePlaceDetailQueryDtoWithRent(checkInDate, roomList);
//        } else {
//            return makePlaceDetailQueryDtoWithoutRent(checkInDate, checkOutDate, roomList);
//        }

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException("Place Not Found"));

        List<RoomQueryDto> roomQueryDto = roomList.stream()
                .map(room -> {
                    return RoomQueryDto.from(room, checkInDate, checkOutDate, null);
                })
                .collect(Collectors.toList());

        return new PlaceInfoQueryDto(place, roomQueryDto);
    }


    @Transactional(readOnly = true)
    public SearchResponseDto queryPlacesInRegion(LocalDate checkInDate, LocalDate checkOutDate,
                                                 Integer guestCount, String regionName) {

        List<Place> placeList = placeRepository.queryPlacesInRegion(regionName);
        return roomListToPlaceListQueryDtoList(placeList, checkInDate, checkOutDate);

    }


    @Transactional(readOnly = true)
    public SearchResponseDto queryPlaceInCategory(LocalDate checkInDate, LocalDate checkOutDate,
                                                  Integer guestCount, String categoryName) {

        PlaceCategory placeCategory = PlaceCategory.fromString(categoryName);

        List<Place> placeList = placeRepository.queryPlaceInCategory(categoryName, placeCategory);
        return roomListToPlaceListQueryDtoList(placeList, checkInDate, checkOutDate);
    }
}
