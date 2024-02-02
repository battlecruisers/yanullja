package com.battlecruisers.yanullja.review;


import static com.battlecruisers.yanullja.member.domain.QMember.member;
import static com.battlecruisers.yanullja.review.domain.QReview.review;
import static com.battlecruisers.yanullja.review.domain.QReviewImage.reviewImage;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;
import static com.querydsl.jpa.JPAExpressions.select;

import com.battlecruisers.yanullja.review.domain.QReview;
import com.battlecruisers.yanullja.review.domain.Review;
import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSampleDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import com.battlecruisers.yanullja.review.dto.ReviewStatisticsDto;
import com.battlecruisers.yanullja.review.exception.NoReviewsException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory query;


    @Override
    public Slice<ReviewDetailDto> findReviews(ReviewSearchCond cond,
        Pageable pageable) {
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
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<ReviewDetailDto> content = reviews.stream()
            .map(ReviewDetailDto::from)
            .collect(Collectors.toList());

        return new SliceImpl<>(content, pageable, reviews.size() > pageable.getPageSize());
    }


    private BooleanExpression roomIdEq(Long roomId) {
        if (roomId == null) {
            return null;
        }
        return review.room.id.eq(roomId);
    }


    private OrderSpecifier<?> reviewSort(ReviewSearchCond cond) {
        if (cond.getOrderProperty().equals("totalRate")) {
            if (cond.getDirection().equals(Order.ASC)) {
                return review.totalRate.asc();
            } else {
                return review.totalRate.desc();
            }
        }
        return review.createdDate.desc();
    }


    @Override
    public ReviewStatisticsDto findReviewStatistics(Long placeId, Long roomId) {
        QReview reviewRate = new QReview("reviewRate");
        List<Tuple> tuples = query
                .select(
                        select(review.count())
                                .from(review)
                                .where(review.place.id.eq(placeId)),
                        select(reviewRate.totalRate.avg())
                                .from(reviewRate)
                                .where(review.place.id.eq(placeId)),
                        select(reviewRate.cleanlinessRate.avg())
                                .from(reviewRate)
                                .where(review.place.id.eq(placeId)),
                        select(reviewRate.convenienceRate.avg())
                                .from(reviewRate)
                                .where(review.place.id.eq(placeId)),
                        select(reviewRate.kindnessRate.avg())
                                .from(reviewRate)
                                .where(review.place.id.eq(placeId)),
                        select(reviewRate.locationRate.avg())
                                .from(reviewRate)
                                .where(review.place.id.eq(placeId))
                )
                .from(review)
                .where(
                        review.place.id.eq(placeId)
                )
                .fetch();

        if (tuples.size() == 0) throw new NoReviewsException();

        Tuple firstData = tuples.get(0);
        return new ReviewStatisticsDto(
                firstData.get(0, Long.class),
                firstData.get(1, Double.class),
                firstData.get(2, Double.class),
                firstData.get(3, Double.class),
                firstData.get(4, Double.class),
                firstData.get(5, Double.class)
        );
    }


    @Override
    public ReviewSampleDto findReviewSamples(Long placeId, Long roomId) {
        QReview reviewRate = new QReview("reviewRate");
        List<Tuple> tuples = query
                .select(
                        review,
                        select(review.count())
                                .from(review)
                                .where(
                                        review.place.id.eq(placeId),
                                        roomIdEq(roomId)
                                ),
                        select(reviewRate.totalRate.avg())
                                .from(reviewRate)
                                .where(review.place.id.eq(placeId))
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

        if (tuples.size() == 0) throw new NoReviewsException();

        ArrayList<ReviewDetailDto> reviews = new ArrayList<>();
        for (Tuple t : tuples) {
            reviews.add(ReviewDetailDto.from(t.get(review)));
        }

        Tuple firstData = tuples.get(0);
        return new ReviewSampleDto(
                firstData.get(1, Long.class),
                firstData.get(2, Double.class),
                reviews
        );
    }

}
