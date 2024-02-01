package com.battlecruisers.yanullja.review;

import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import com.battlecruisers.yanullja.review.dto.ReviewStatisticsDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomReviewRepository {


    Slice<ReviewDetailDto> findReviews(ReviewSearchCond cond,
        Pageable pageable);

    ReviewStatisticsDto findReviewInfo(Long placeId, Long roomId);

}
