package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.PlaceInfoQueryDto;
import com.battlecruisers.yanullja.place.dto.PlaceQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.place.dto.SearchResponseDto;
import com.battlecruisers.yanullja.place.searchcondition.CapacityCondition;
import com.battlecruisers.yanullja.place.searchcondition.MaxPriceCondition;
import com.battlecruisers.yanullja.place.searchcondition.MinPriceCondition;
import com.battlecruisers.yanullja.place.searchcondition.PlaceSearchCondition;
import com.battlecruisers.yanullja.place.searchcondition.ReservationCondition;
import com.battlecruisers.yanullja.place.searchcondition.ThemeCondition;
import com.battlecruisers.yanullja.reservation.ReservationRepository;
import com.battlecruisers.yanullja.reservation.ReservationService;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.dto.RoomQueryDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;

    public static Boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static Integer getWeekDayCount(LocalDate checkInDate,
        LocalDate checkOutDate) {

        Integer weekDayCount = 0;
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate);
            date = date.plusDays(1)) {
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
            .filter(
                coupon -> (coupon.getValidityStartDate().isBefore(checkInDate)
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


    private List<PlaceQueryDto> sortDtoList(
        List<PlaceQueryDto> placeQueryDtoList, String sort) {
        SortType sortType = SortType.valueOf(sort);
        if (sortType == SortType.PRICE_LOW) {
            return placeQueryDtoList.stream()
                .sorted(Comparator.comparing(PlaceQueryDto::getMinimumPrice))
                .collect(Collectors.toList());
        } else if (sortType == SortType.PRICE_HIGH) {
            return placeQueryDtoList.stream()
                .sorted(Comparator.comparing(PlaceQueryDto::getMinimumPrice)
                    .reversed())
                .collect(Collectors.toList());
        }
        return placeQueryDtoList;
    }


    private List<PlaceQueryDto> toPlaceQueryDtoList(List<Place> placeList,
        LocalDate checkInDate, LocalDate checkOutDate, String sort) {
        List<PlaceQueryDto> placeQueryDtoList = placeList.stream()
            .map(place -> {
                return PlaceQueryDto.from(place, checkInDate, checkOutDate);
            })
            .collect(Collectors.toList());

        //정렬 조건이 있으면 정렬하기.
        if (sort != null && !sort.isBlank()) {
            placeQueryDtoList = sortDtoList(placeQueryDtoList, sort);
        }
        return placeQueryDtoList;
    }

    private SearchResponseDto toSearchResponseDto(
        List<PlaceQueryDto> placeQueryDtoList) {
        return new SearchResponseDto(placeQueryDtoList);
    }

    private PlaceInfoQueryDto getPlaceRoomInfoQueryDto(Long placeId,
        LocalDate checkInDate,
        LocalDate checkOutDate, List<Room> roomList) {

        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new NotFoundException("Place Not Found"));

        List<RoomQueryDto> roomQueryDto = roomList.stream()
            .map(room -> {
                List<Reservation> reservations = room.getReservations();
                Integer maxAvailableRoomCount
                    = reservationService.getMaxAvailableRoomCount(reservations,
                    room, checkInDate, checkOutDate);
                return RoomQueryDto.from(room, checkInDate, checkOutDate,
                    maxAvailableRoomCount);
            })
            .collect(Collectors.toList());

        return new PlaceInfoQueryDto(place, roomQueryDto);
    }


    @Transactional(readOnly = true)
    public Page<PlaceQueryDto> searchPlaceWithStrategies(Pageable pageable,
        SearchConditionDto searchConditionDto) {

        List<Place> placeList = placeRepository.searchPlacesWithConditions(
            searchConditionDto);

        List<PlaceSearchCondition> searchCondtionList = makeSearchCondtionList(
            searchConditionDto);

        //검색조건에 맞는 장소 검색
        for (PlaceSearchCondition condition : searchCondtionList) {
            placeList = condition.filterPlaces(placeList, searchConditionDto);
        }

        //DTOList로 변환
        List<PlaceQueryDto> placeQueryDtoList = toPlaceQueryDtoList(placeList,
            searchConditionDto.getStartDate(),
            searchConditionDto.getEndDate(), searchConditionDto.getSort());

        //페이징 처리
        Page<PlaceQueryDto> page = new PageImpl<>(placeQueryDtoList, pageable,
            placeQueryDtoList.size());
        return page;

    }

    private List<PlaceSearchCondition> makeSearchCondtionList(
        SearchConditionDto searchConditionDto) {

        List<PlaceSearchCondition> conditionList = new ArrayList<>();

        Integer minPrice = searchConditionDto.getMinPrice();
        Integer maxPrice = searchConditionDto.getMaxPrice();
        Integer guest = searchConditionDto.getGuest();
        String theme = searchConditionDto.getTheme();

        /*
        capacity 체크
         */
        if (guest != null) {
            conditionList.add(new CapacityCondition());
        }

        /*
         minPrice 체크
         */
        if (minPrice != null) {
            conditionList.add(new MinPriceCondition());
        }

        /*
        maxPrice 체크
         */
        if (maxPrice != null) {
            conditionList.add(new MaxPriceCondition());
        }

        /*
         예약 가능 체크
         */
        conditionList.add(new ReservationCondition(reservationRepository,
            reservationService));

        /*
        Theme 체크
         */
        if (theme != null) {
            conditionList.add(new ThemeCondition());
        }
        return conditionList;
    }

    @Transactional(readOnly = true)
    public PlaceInfoQueryDto queryPlace(Long placeId, LocalDate checkInDate,
        LocalDate checkOutDate, Integer guestCount) {

        List<Room> roomList
            = placeRepository.queryPlace(placeId, checkInDate, checkOutDate,
            guestCount);

        return getPlaceRoomInfoQueryDto(placeId, checkInDate, checkOutDate,
            roomList);
    }


    @Transactional(readOnly = true)
    public Page<PlaceQueryDto> queryPlacesInRegion(Pageable pageable,
        LocalDate checkInDate, LocalDate checkOutDate,
        Integer guestCount, String regionName) {

        List<Place> placeList = placeRepository.queryPlacesInRegion(regionName,
            pageable);
        placeList = checkCapacity(placeList, guestCount);
        List<PlaceQueryDto> placeQueryDtoList = toPlaceQueryDtoList(placeList,
            checkInDate, checkOutDate, null
        );
        return new PageImpl<>(placeQueryDtoList, pageable,
            placeQueryDtoList.size());
    }


    @Transactional(readOnly = true)
    public SearchResponseDto queryPlaceInCategory(LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer guestCount, String categoryName) {

        PlaceCategory placeCategory = PlaceCategory.fromString(categoryName);

        List<Place> placeList = placeRepository.queryPlaceInCategory(
            categoryName, placeCategory);
        placeList = checkCapacity(placeList, guestCount);
        return toSearchResponseDto(
            toPlaceQueryDtoList(placeList, checkInDate, checkOutDate, null));
    }

    @Transactional(readOnly = true)
    public SearchResponseDto queryPlacesRanking(Pageable pageable,
        LocalDate checkInDate, LocalDate checkOutDate) {

        List<Place> placeList = placeRepository.queryPlacesRanking(pageable);
        return toSearchResponseDto(
            toPlaceQueryDtoList(placeList, checkInDate, checkOutDate, null));
    }
}
