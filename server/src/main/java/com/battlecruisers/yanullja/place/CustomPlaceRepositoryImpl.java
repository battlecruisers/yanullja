package com.battlecruisers.yanullja.place;


import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.battlecruisers.yanullja.place.domain.QPlace.place;
import static com.battlecruisers.yanullja.region.domain.QSubRegion.subRegion;
import static com.battlecruisers.yanullja.reservation.domain.QReservation.reservation;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;

@RequiredArgsConstructor
public class CustomPlaceRepositoryImpl implements CustomPlaceRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Place> searchPlacesWithConditions(
            SearchConditionDto searchConditionDto) {

        return jpaQueryFactory.selectFrom(place).distinct()
                .leftJoin(place.subRegion, subRegion).fetchJoin()
                .leftJoin(place.roomList).fetchJoin()
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
                        goeGuestCount(guestCount)
                )
                .fetch();
    }

    @Override
    public List<Place> queryPlacesInRegion(String regionName, Pageable pageable) {
        return jpaQueryFactory.selectFrom(place).distinct()
                .join(place.roomList).fetchJoin()
                .where(place.subRegion.name.eq(regionName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
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
    public List<Place> queryPlacesRanking(Pageable pageable) {
        return jpaQueryFactory.select(place)
                .from(reservation)
                .leftJoin(reservation.room, room)
                .leftJoin(room.place, place)
                .groupBy(place)
                .orderBy(reservation.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression goeGuestCount(Integer guestCount) {
        return room.capacity.goe(guestCount);
    }


    private BooleanBuilder makeBooleanBuilderForSearch(
            SearchConditionDto searchConditionDto) {

        BooleanBuilder builder = new BooleanBuilder();
        Integer capacity = searchConditionDto.getGuest();
        String keyword = searchConditionDto.getName();

        if (keyword != null && !keyword.isBlank() && !keyword.equals("null")) {
            builder.and(eqKeyword(keyword));
        }
        return builder;
    }


    private BooleanExpression eqKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }

        return place.name.containsIgnoreCase(keyword);
    }
}
