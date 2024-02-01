package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.domain.Review;
import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSaveDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import com.battlecruisers.yanullja.review.dto.ReviewStatisticsDto;
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
    public ReviewStatisticsDto getReviewInfo(Long placeId, Long roomId) {
        return reviewRepository.findReviewInfo(placeId, roomId);
    }

    public Long saveReview(ReviewSaveDto form) {
        Review review = Review.from(form);
        return reviewRepository.save(review)
            .getId();
    }

}
