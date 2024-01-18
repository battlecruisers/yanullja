package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.domain.Review;
import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.battlecruisers.yanullja.member.domain.QMember.member;
import static com.battlecruisers.yanullja.review.domain.QReview.review;
import static com.battlecruisers.yanullja.review.domain.QReviewImage.reviewImage;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;

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
                .groupBy(reviewImage.review.id)
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
                .map(review -> ReviewDetailDto.createNewReviewDetail(review))
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }


    private BooleanExpression roomIdEq(Long roomId) {
        if (roomId == null) {
            return null;
        }
        return review.room.id.eq(roomId);
    }


    private OrderSpecifier reviewSort(ReviewSearchCond cond) {
        switch (cond.getOrderProperty()) {
            case "totalRate":
                if (cond.getDirection().equals(Order.ASC))
                    return review.totalRate.asc();
                else
                    return review.totalRate.desc();
            default:
                return new OrderSpecifier(cond.getDirection(), review.createdDate);
        }
    }


}
