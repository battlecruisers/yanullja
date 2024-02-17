package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.domain.Review;
import com.battlecruisers.yanullja.review.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    @Transactional(readOnly = true)
    public Slice<ReviewDetailDto> getReviewDetails(ReviewSearchCond cond,
        Pageable pageable) {
        return reviewRepository.findReviews(cond, pageable);
    }


    @Transactional(readOnly = true)
    public ReviewSampleDto getReviewSamples(Long placeId, Long roomId) {
        return reviewRepository.findReviewSamples(placeId, roomId);
    }

    @Transactional(readOnly = true)
    public ReviewStatisticsDto getReviewStatistics(Long placeId, Long roomId) {
        return reviewRepository.findReviewStatistics(placeId, roomId);
    }


    public Long saveReview(ReviewSaveDto form, Long memberId) {
        Review review = Review.from(form, memberId);
        return reviewRepository.save(review)
            .getId();
    }

}
