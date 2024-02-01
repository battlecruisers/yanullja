package com.battlecruisers.yanullja.review.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewSampleDto {

    private Long totalCount;

    private Double totalRateAvg;

    private List<ReviewDetailDto> reviews;

    public ReviewSampleDto(Long totalCount, Double totalRateAvg, List<ReviewDetailDto> reviews) {
        this.totalCount = totalCount;
        this.totalRateAvg = totalRateAvg;
        this.reviews = reviews;
    }
}
