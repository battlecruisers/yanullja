package com.battlecruisers.yanullja.place;


import static com.battlecruisers.yanullja.place.domain.QPlace.place;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;
import static com.battlecruisers.yanullja.theme.domain.QTheme.theme;

import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.ThemeType;
import com.battlecruisers.yanullja.theme.domain.Theme;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomPlaceRepositoryImpl implements CustomPlaceRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Room> searchPlacesWithConditions(String keyword,
        SearchConditionDto searchConditionDto, List<ThemeType> themeList, SortType sortType) {

        return jpaQueryFactory.selectFrom(room).distinct()
            .join(room.place, place).fetchJoin()
            .leftJoin(room.roomInfoList).fetchJoin()
            .where(
                makeBooleanBuilderForSearch(keyword, searchConditionDto, themeList))
            .orderBy(makeOrderSpecifierForSearch(sortType))
            .fetch();

    }

    private BooleanBuilder makeBooleanBuilderForSearch(String keyword,
        SearchConditionDto searchConditionDto, List<ThemeType> themeList) {

        BooleanBuilder builder = new BooleanBuilder();
        LocalDate checkinDate = searchConditionDto.getCheckinDate();
        LocalDate checkoutDate = searchConditionDto.getCheckoutDate();
        Integer minPrice = searchConditionDto.getMinPrice();
        Integer maxPrice = searchConditionDto.getMaxPrice();
        Integer capacity = searchConditionDto.getCapacity();
        Integer rentable = searchConditionDto.getRentable();
        Integer stayable = searchConditionDto.getStayable();
        Integer applicable = searchConditionDto.getApplicable();

        if (keyword != null && !keyword.isBlank()) {
            builder.and(eqKeyword(keyword));
        }

        if (checkinDate != null && checkoutDate != null) {
            builder.and(checkAvailableDate(checkinDate, checkoutDate));
        }

        if (minPrice != null && maxPrice != null) {
            builder.and(checkPrice(minPrice, maxPrice));
        }

        if (capacity != null) {
            builder.and(checkCapacity(capacity));
        }

        if (themeList != null && !themeList.isEmpty()) {
            builder.and(checkTheme(themeList));
        }

        if (applicable != null && checkinDate != null && checkoutDate != null) {
            builder.and(checkApplicable(checkinDate, checkoutDate, applicable));
        }

        if (rentable != null && checkinDate != null && checkoutDate != null) {
            builder.and(checkRentable(checkinDate, checkoutDate, rentable));
        }

        if (stayable != null && checkinDate != null && checkoutDate != null) {
            builder.and(checkStayable(checkinDate, checkoutDate, stayable));
        }

        return builder;
    }

    private BooleanExpression checkApplicable(LocalDate checkinDate, LocalDate checkoutDate,
        Integer applicable) {
        return Expressions.asBoolean(true).isTrue();
    }

    private OrderSpecifier makeOrderSpecifierForSearch(SortType sortType) {

        OrderSpecifier orderSpecifier = null;
        if (sortType == SortType.PRICE_LOW) {

        } else if (sortType == SortType.PRICE_HIGH) {

        } else if (sortType == SortType.REVIEW_GOOD) {

        } else if (sortType == SortType.REVIEW_MANY) {

        } else if (sortType == SortType.BOOKMARK_MANY) {

        }
        return orderSpecifier;
    }

    private BooleanExpression eqKeyword(String keyword) {
        //TODO : 숙소의 이름에 주어진 키워드가 존재하는지 체크하는 로직
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }

        return room.place.name.contains(keyword);
    }

    private BooleanExpression checkAvailableDate(LocalDate checkinDate, LocalDate checkoutDate) {
        //TODO : 입력 받은 체크인 날짜, 체크아웃 날짜 사이에 모두 해당 숙소가 이용 가능한지 체크하는 로직
        return Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression checkPrice(Integer minPrice, Integer maxPrice) {
        //TODO : 사이 날짜가 주말인지, 평일인지 체크한 후에 가격의 합을 구해서 해당 범위 내에 있는지 체크하는 로직
        return Expressions.asBoolean(true).isTrue();
    }

    // 사용 인원이 최대 인원보다 적은지 체크하는 로직
    private BooleanExpression checkCapacity(Integer capacity) {

        if (capacity == null) {
            return null;
        }
//        return room.maxCapacity.goe(capacity);
        return Expressions.asBoolean(true).isTrue();

    }

    private BooleanExpression checkTheme(List<ThemeType> themeTypeList) {
        //TODO : ThemeType을 모두 가지고 있는지 체크하는 로직
        List<Theme> themeList = jpaQueryFactory.selectFrom(theme)
            .where(theme.type.in(themeTypeList))
            .fetch();

        for (Theme theme : themeList) {
            if (place.themeList.contains(theme) == Expressions.asBoolean(false).isFalse()) {
                return Expressions.asBoolean(false).isFalse();
            }
        }
        return Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression checkRentable(LocalDate checkinDate, LocalDate checkoutDate,
        Integer rentable) {
        if (rentable == null) {
            return null;
        }

        //TODO : 제공한 기간에서 대실 가능한 방이 있는지 체크하는 로직
        return Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression checkStayable(LocalDate checkinDate, LocalDate checkoutDate,
        Integer stayable) {
        if (stayable == null) {
            return null;
        }

        //TODO : 제공한 기간에서 숙박 가능한 방이 있는지 체크하는 로직
        return Expressions.asBoolean(true).isTrue();
    }


}
