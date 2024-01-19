package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewInfo;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/reviews")
    public ResponseEntity<Object> fetchReviews(@RequestParam(value = "placeId") Long placeId, @RequestParam(value = "roomId", required = false) Long roomId,
                                               @RequestParam(value = "photo", required = false, defaultValue = "false") boolean photo,
                                               @PageableDefault(size = 15, sort = "createdDate") Pageable pageable) {

        ReviewSearchCond cond = new ReviewSearchCond(placeId, roomId, photo, pageable);
        Slice<ReviewDetailDto> reviews = reviewService.getReviewDetails(cond, pageable);

        return ResponseEntity
                .ok()
                .body(reviews);
    }

    /**
     * 나중에 Place Controller 생기면 옮겨야 함
     */
    @GetMapping("/places/{placeId}/review")
    public ResponseEntity<Object> fetchReviews(@PathVariable(value = "placeId") Long placeId, @RequestParam(value = "roomId", required = false) Long roomId) {

        ReviewInfo reviewInfo = reviewService.getReviewInfo(placeId, roomId);

        return ResponseEntity
                .ok()
                .body(reviewInfo);
    }
}
