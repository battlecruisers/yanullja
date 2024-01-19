package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
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


    public Slice<ReviewDetailDto> getReviewDetails(ReviewSearchCond cond, Pageable pageable) {
        return reviewRepository.findReviews(cond, pageable);
    }

}
