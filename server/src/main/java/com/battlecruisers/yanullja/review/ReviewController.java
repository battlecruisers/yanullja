package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("")
    public ResponseEntity<Object> fetchReviews(@RequestParam(value = "placeId") Long placeId, @RequestParam(value = "roomId", required = false) Long roomId,
                                               @RequestParam(value = "photo", required = false, defaultValue = "false") boolean photo,
                                               @PageableDefault(size = 15, sort = "createdDate") Pageable pageable) {

        ReviewSearchCond cond = new ReviewSearchCond(placeId, roomId, photo, pageable);
        Slice<ReviewDetailDto> reviews = reviewService.getReviewDetails(cond, pageable);

        return ResponseEntity
                .ok()
                .body(reviews);
    }
}
