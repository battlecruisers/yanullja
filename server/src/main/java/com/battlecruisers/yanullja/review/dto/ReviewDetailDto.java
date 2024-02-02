package com.battlecruisers.yanullja.review.dto;


import com.battlecruisers.yanullja.review.domain.Review;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ReviewDetailDto {

    private Long id;

    private Double totalRate;

    private String writer;

    private String roomName;

    private LocalDateTime creationDate;

    private String content;

    private List<String> roomImageUrls;

    private ReviewDetailDto(Review review) {
        this.id = review.getId();
        this.totalRate = review.getTotalRate();
        this.writer = review.getMember().getNickName();
        this.roomName = review.getRoom().getName();
        this.creationDate = review.getCreatedDate();
        this.content = review.getContent();
        this.roomImageUrls = review.getReviewImages().stream()
            .map(img -> img.getImageUrl())
            .collect(Collectors.toList());
    }

    public static ReviewDetailDto from(Review review) {
        return new ReviewDetailDto(review);
    }
}
