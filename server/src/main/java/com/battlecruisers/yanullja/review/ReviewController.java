package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.review.dto.ReviewSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Object> writeReviews(
        @RequestBody ReviewSaveDto saveForm) {

        reviewService.saveReview(saveForm);

        return ResponseEntity
            .ok()
            .build();
    }

}
