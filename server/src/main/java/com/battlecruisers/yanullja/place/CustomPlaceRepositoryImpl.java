package com.battlecruisers.yanullja.place;


import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.ThemeType;
import com.battlecruisers.yanullja.theme.domain.Theme;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.battlecruisers.yanullja.place.domain.QPlace.place;
import static com.battlecruisers.yanullja.region.domain.QSubRegion.subRegion;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;
import static com.battlecruisers.yanullja.theme.domain.QTheme.theme;

@RequiredArgsConstructor
public class CustomPlaceRepositoryImpl implements CustomPlaceRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Place> searchPlacesWithConditions(
            SearchConditionDto searchConditionDto) {

        return jpaQueryFactory.selectFrom(place).distinct()
                .leftJoin(place.roomList).fetchJoin()
                .leftJoin(place.subRegion, subRegion).fetchJoin()
                .where(
                        makeBooleanBuilderForSearch(searchConditionDto)
                )
                .fetch();

    }

    @Override
    public List<Room> queryPlace(Long placeId, LocalDate checkInDate, LocalDate checkOutDate,
                                 Integer guestCount) {
        return jpaQueryFactory.selectFrom(room).distinct()
                .join(room.place, place).fetchJoin()
                .leftJoin(room.coupons).fetchJoin()
                .where(room.place.id.eq(placeId))
                .where(
                        canReserve(checkInDate, checkOutDate, placeId),
                        goeGuestCount(guestCount)
                )
                .fetch();
    }

    @Override
    public List<Place> queryPlacesInRegion(String regionName) {
        return jpaQueryFactory.selectFrom(place).distinct()
                .join(place.roomList).fetchJoin()
                .where(place.subRegion.name.eq(regionName))
                .fetch();
    }

    @Override
    public List<Place> queryPlaceInCategory(String categoryName, PlaceCategory placeCategory) {
        return jpaQueryFactory.selectFrom(place).distinct()
                .join(place.roomList).fetchJoin()
                .where(place.category.eq(placeCategory))
                .fetch();
    }

    @Override
    public List<Place> queryPlacesRanking() {
        return jpaQueryFactory.selectFrom(place).distinct()
                .join(place.roomList).fetchJoin()
                .fetch();
    }

    private BooleanExpression goeGuestCount(Integer guestCount) {
        return room.capacity.goe(guestCount);
    }

    private BooleanExpression canReserve(LocalDate checkInDate, LocalDate checkOutDate,
                                         Long placeId) {
        //TODO : 숙소 내의 각 방에 대해서 예약 목록 체크해서 예약 가능한지 확인하기

        return null;
    }

    private BooleanBuilder makeBooleanBuilderForSearch(
            SearchConditionDto searchConditionDto) {

        BooleanBuilder builder = new BooleanBuilder();
        Integer capacity = searchConditionDto.getGuest();
        String keyword = searchConditionDto.getName();

        if (keyword != null && !keyword.isBlank() && !keyword.equals("null")) {
            builder.and(eqKeyword(keyword));
        }


//        if (capacity != null) {
//            builder.and(checkCapacity(capacity));
//        }

//        if (themeList != null && !themeList.isEmpty()) {
//            builder.and(checkTheme(themeList));
//        }

        return builder;
    }


    private BooleanExpression eqKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }

        return place.name.containsIgnoreCase(keyword);
    }


    private BooleanExpression checkTheme(List<ThemeType> themeTypeList) {
        List<Theme> themeList = jpaQueryFactory.selectFrom(theme)
                .where(theme.type.in(themeTypeList))
                .fetch();

        for (Theme theme : themeList) {
            if (place.themeList.contains(theme).equals(Expressions.asBoolean(false).isFalse())) {
                return Expressions.asBoolean(false).isFalse();
            }
        }
        return Expressions.asBoolean(true).isTrue();
    }


}
