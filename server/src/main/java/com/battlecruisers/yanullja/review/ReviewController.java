package com.battlecruisers.yanullja.review;


import com.battlecruisers.yanullja.common.exception.CustomValidationException;
import com.battlecruisers.yanullja.common.jsendresponse.JSendResponse;
import com.battlecruisers.yanullja.member.domain.SecurityMember;
import com.battlecruisers.yanullja.review.dto.ReviewSaveDto;
import com.battlecruisers.yanullja.review.example.WriteReviewOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "후기 등록 성공"),
        @ApiResponse(responseCode = "400", description = "검증 오류", content = @Content(schema = @Schema(implementation = JSendResponse.class))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = JSendResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
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
