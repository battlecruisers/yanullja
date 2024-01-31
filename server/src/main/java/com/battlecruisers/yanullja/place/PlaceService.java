package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.PlaceInfoQueryDto;
import com.battlecruisers.yanullja.place.dto.PlaceQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.place.dto.SearchResponseDto;
import com.battlecruisers.yanullja.reservation.ReservationRepository;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.dto.RoomQueryDto;
import com.battlecruisers.yanullja.theme.ThemeRepository;
import com.battlecruisers.yanullja.theme.ThemeType;
import com.battlecruisers.yanullja.theme.domain.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

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

    private Boolean checkMinPrice(Place place, LocalDate checkInDate, LocalDate checkOutDate,
                                  Integer minPrice) {
        return place.getMinimumPrice(checkInDate, checkOutDate) >= minPrice;
    }

    private Boolean checkMaxPrice(Place place, LocalDate checkInDate, LocalDate checkOutDate,
                                  Integer maxPrice) {
        return place.getMinimumPrice(checkInDate, checkOutDate) <= maxPrice;
    }

    private List<Place> checkCapacity(List<Place> placeList, Integer capacity) {
        return placeList.stream()
                .filter(place -> {
                    boolean flag = false;
                    List<Room> roomList = place.getRoomList();
                    for (Room room : roomList) {
                        if (room.getCapacity() >= capacity) {
                            flag = true;
                            break;
                        }
                    }
                    return flag;
                })
                .collect(Collectors.toList());
    }


    private List<PlaceQueryDto> sortDtoList(List<PlaceQueryDto> placeQueryDtoList, String sort) {
        SortType sortType = SortType.valueOf(sort);
        if (sortType == SortType.PRICE_LOW) {
            return placeQueryDtoList.stream()
                    .sorted(Comparator.comparing(PlaceQueryDto::getMinimumPrice))
                    .collect(Collectors.toList());
        } else if (sortType == SortType.PRICE_HIGH) {
            return placeQueryDtoList.stream()
                    .sorted(Comparator.comparing(PlaceQueryDto::getMinimumPrice).reversed())
                    .collect(Collectors.toList());
        }
        return placeQueryDtoList;
    }

    private List<Place> checkTheme(List<Place> placeList, List<ThemeType> themeTypeList) {
        Map<Place, List<Theme>> placeThemeMap = placeList.stream()
                .collect(Collectors.toMap(place -> place, Place::getThemeList));
        return placeList.stream()
                .filter(place -> {
                    List<ThemeType> placeThemeTypeList = placeThemeMap.get(place).stream()
                            .map(Theme::getType)
                            .collect(Collectors.toList());
                    return placeThemeTypeList.containsAll(themeTypeList);
                })
                .collect(Collectors.toList());
    }

    private List<Place> checkPrice(List<Place> placeList, Integer maxPrice, LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice) {
        return placeList.stream()
                .filter(place -> {
                    //최대 가격 체크
                    if (maxPrice == null) return true;
                    return checkMaxPrice(place, checkInDate, checkOutDate, maxPrice);
                })
                .filter(place -> {
                    //최소 가격 체크
                    if (minPrice == null) return true;
                    return checkMinPrice(place, checkInDate, checkOutDate, maxPrice);
                })
                .collect(Collectors.toList());
    }

    public List<ThemeType> getThemeTypeList(String theme) {

        if (theme != null && !theme.isBlank()) {
            String[] themes = theme.split(",");

            return Arrays.stream(themes)
                    .map(ThemeType::valueOf)
                    .collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    private List<PlaceQueryDto> toPlaceQueryDtoList(List<Place> placeList,
                                                    LocalDate checkInDate, LocalDate checkOutDate) {
        return placeList.stream()
                .map(place -> {
                    return PlaceQueryDto.from(place, checkInDate, checkOutDate);
                })
                .collect(Collectors.toList());
    }

    private SearchResponseDto toSearchResponseDto(List<PlaceQueryDto> placeQueryDtoList) {
        return new SearchResponseDto(placeQueryDtoList);
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
    public Page<PlaceQueryDto> searchPlaces(Pageable pageable, SearchConditionDto searchConditionDto) {

        String theme = searchConditionDto.getTheme();

        final List<ThemeType> themeTypeList = getThemeTypeList(theme);

        List<Place> placeList = placeRepository.searchPlacesWithConditions(
                searchConditionDto);

        LocalDate checkInDate = searchConditionDto.getStartDate();
        LocalDate checkOutDate = searchConditionDto.getEndDate();
        Integer minPrice = searchConditionDto.getMinPrice();
        Integer maxPrice = searchConditionDto.getMaxPrice();
        String sort = searchConditionDto.getSort();
        Integer guest = searchConditionDto.getGuest();

        /*
        capacity 체크
         */
        if (guest != null) {
            placeList = checkCapacity(placeList, guest);
        }

        /*
         가격 limit 체크
         */
        if (minPrice != null || maxPrice != null) {
            placeList = checkPrice(placeList, maxPrice, checkInDate, checkOutDate, minPrice);
        }

        /*
         예약 가능 체크
         */
//        Map<Room, List<Reservation>> roomReservationMap
//                = reservationRepository.queryReservationsInDateRange(placeList, checkInDate, checkOutDate)
//                .stream()
//                .collect(Collectors.groupingBy(Reservation::getRoom));

        //TODO : 각 방이 예약 가능한지 체크하고 place로 넘기기

        /*
        Theme 체크
         */
        if (!themeTypeList.isEmpty()) {
            placeList = checkTheme(placeList, themeTypeList);
        }


        /*
        쿠폰 적용 가능
         */


        List<PlaceQueryDto> placeQueryDtoList = toPlaceQueryDtoList(placeList, searchConditionDto.getStartDate(),
                searchConditionDto.getEndDate());

        /*
         * 정렬
         */
        if (sort != null && !sort.isBlank()) {
            placeQueryDtoList = sortDtoList(placeQueryDtoList, sort);
        }


        Page<PlaceQueryDto> page = new PageImpl<>(placeQueryDtoList, pageable, placeQueryDtoList.size());
        return page;


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
    }

    @Transactional(readOnly = true)
    public PlaceInfoQueryDto queryPlace(Long placeId, LocalDate checkInDate,
                                        LocalDate checkOutDate, Integer guestCount) {

        List<Room> roomList
                = placeRepository.queryPlace(placeId, checkInDate, checkOutDate, guestCount);

        return getPlaceRoomInfoQueryDto(placeId, checkInDate, checkOutDate, roomList);
    }


    @Transactional(readOnly = true)
    public Page<PlaceQueryDto> queryPlacesInRegion(Pageable pageable, LocalDate checkInDate, LocalDate checkOutDate,
                                                   Integer guestCount, String regionName) {

        List<Place> placeList = placeRepository.queryPlacesInRegion(regionName, pageable);
        placeList = checkCapacity(placeList, guestCount);
        List<PlaceQueryDto> placeQueryDtoList = toPlaceQueryDtoList(placeList, checkInDate, checkOutDate);
        return new PageImpl<>(placeQueryDtoList, pageable, placeQueryDtoList.size());
    }


    @Transactional(readOnly = true)
    public SearchResponseDto queryPlaceInCategory(LocalDate checkInDate, LocalDate checkOutDate,
                                                  Integer guestCount, String categoryName) {

        PlaceCategory placeCategory = PlaceCategory.fromString(categoryName);

        List<Place> placeList = placeRepository.queryPlaceInCategory(categoryName, placeCategory);
        placeList = checkCapacity(placeList, guestCount);
        return toSearchResponseDto(toPlaceQueryDtoList(placeList, checkInDate, checkOutDate));
    }

    @Transactional(readOnly = true)
    public SearchResponseDto queryPlacesRanking(Pageable pageable, LocalDate checkInDate, LocalDate checkOutDate) {

        List<Place> placeList = placeRepository.queryPlacesRanking(pageable);
//        List<Place> placeList = reservationList.stream()
//                .map(tuple -> tuple.get(0, Place.class))
//                .collect(Collectors.toList());
        return toSearchResponseDto(toPlaceQueryDtoList(placeList, checkInDate, checkOutDate));
    }
}
