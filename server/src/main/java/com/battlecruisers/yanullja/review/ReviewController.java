package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.common.exception.CustomValidationException;
import com.battlecruisers.yanullja.member.domain.SecurityMember;
import com.battlecruisers.yanullja.review.dto.ReviewSaveDto;
import com.battlecruisers.yanullja.review.example.WriteReviewOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "후기", description = "후기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class ReviewController {

    private final ReviewService reviewService;

    @WriteReviewOperation
    @PostMapping("/reviews")
    public ResponseEntity<Object> writeReviews(@AuthenticationPrincipal SecurityMember member, @Validated @RequestBody ReviewSaveDto saveForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(
                bindingResult.getAllErrors().stream().findFirst().get()
                    .getDefaultMessage());
        }

        reviewService.saveReview(saveForm, member.getId());

        return ResponseEntity
            .ok()
            .build();
    }

}
