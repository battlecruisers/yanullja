package com.battlecruisers.yanullja.review.dto;


import com.battlecruisers.yanullja.review.domain.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReviewDetailDto {

    private Long id;

    private String writer;

    private String roomName;

    private LocalDateTime createdDate;

    private String content;

    private List<String> imgUrls;

    public ReviewDetailDto(Review review) {
        this.id = review.getId();
        this.writer = review.getMember().getNickName();
        this.roomName = review.getRoom().getName();
        this.createdDate = review.getCreatedDate();
        this.content = review.getContent();
        this.imgUrls = review.getReviewImages().stream()
                .map(img -> img.getImageUrl())
                .collect(Collectors.toList());
    }

    public static ReviewDetailDto createNewReviewDetail(Review review){
        return new ReviewDetailDto(review);
    }
}
