package com.battlecruisers.yanullja.review;

import com.battlecruisers.yanullja.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>,
    CustomReviewRepository {


}
