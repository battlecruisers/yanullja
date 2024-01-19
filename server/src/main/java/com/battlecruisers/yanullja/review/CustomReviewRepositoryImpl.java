package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.domain.QReview;
import com.battlecruisers.yanullja.review.domain.Review;
import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewInfo;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.battlecruisers.yanullja.member.domain.QMember.member;
import static com.battlecruisers.yanullja.review.domain.QReview.review;
import static com.battlecruisers.yanullja.review.domain.QReviewImage.reviewImage;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;
import static com.querydsl.jpa.JPAExpressions.select;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory query;


    @Override
    public Slice<ReviewDetailDto> findReviews(ReviewSearchCond cond, Pageable pageable) {
        List<Review> reviews = query
                .select(review)
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.room, room).fetchJoin()
                .leftJoin(reviewImage)
                .on(review.id.eq(reviewImage.review.id))
                .where(
                        review.place.id.eq(cond.getPlaceId()),
                        roomIdEq(cond.getRoomId()),
                        cond.getHasPhoto() ? reviewImage.review.id.isNotNull() : null
                )
                .distinct()
                .orderBy(reviewSort(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(review.count())
                .innerJoin(review.reviewImages, reviewImage)
                .where(
                        review.place.id.eq(cond.getPlaceId()),
                        roomIdEq(cond.getRoomId())
                );


        List<ReviewDetailDto> content = reviews.stream()
                .map(ReviewDetailDto::createNewReviewDetail)
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }


    private BooleanExpression roomIdEq(Long roomId) {
        if (roomId == null) {
            return null;
        }
        return review.room.id.eq(roomId);
    }


    private OrderSpecifier<?> reviewSort(ReviewSearchCond cond) {
        if (cond.getOrderProperty().equals("totalRate")) {
            if (cond.getDirection().equals(Order.ASC))
                return review.totalRate.asc();
            else
                return review.totalRate.desc();
        }
        return review.createdDate.desc();
    }


    @Override
    public ReviewInfo findReviewInfo(Long placeId, Long roomId) {
        QReview reviewRate = new QReview("reviewRate");
        List<Tuple> data = query
                .select(
                        review,
                        select(reviewRate.totalRate.avg()).from(reviewRate),
                        select(reviewRate.cleanlinessRate.avg()).from(reviewRate),
                        select(reviewRate.convenienceRate.avg()).from(reviewRate),
                        select(reviewRate.kindnessRate.avg()).from(reviewRate),
                        select(reviewRate.locationRate.avg()).from(reviewRate)
                )
                .from(review)
                .join(review.member, member).fetchJoin()
                .join(review.room, room).fetchJoin()
                .leftJoin(reviewImage)
                .on(review.id.eq(reviewImage.review.id))
                .where(
                        review.place.id.eq(placeId),
                        roomIdEq(roomId)
                )
                .distinct()
                .orderBy(review.createdDate.asc())
                .offset(0)
                .limit(10)
                .fetch();


        ArrayList<ReviewDetailDto> reviews = new ArrayList<>();
        for (Tuple t : data) {
            reviews.add(ReviewDetailDto.createNewReviewDetail(t.get(review)));
        }

        return new ReviewInfo(
                data.get(0).get(1, Double.class),
                data.get(0).get(2, Double.class),
                data.get(0).get(3, Double.class),
                data.get(0).get(4, Double.class),
                data.get(0).get(5, Double.class),
                reviews
        );
    }

}
