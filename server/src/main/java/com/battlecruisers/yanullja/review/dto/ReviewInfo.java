package com.battlecruisers.yanullja.review.dto;


import lombok.Data;

import java.util.List;

@Data
public class ReviewInfo {

    private Double totalRateAvg;

    private Double kindnessRateAvg;

    private Double cleanlinessRateAvg;

    private Double convenienceRateAvg;

    private Double locationRateAvg;

    private List<ReviewDetailDto> reviews;


    public ReviewInfo(Double totalRateAvg, Double kindnessRateAvg, Double cleanlinessRateAvg, Double convenienceRateAvg, Double locationRateAvg, List<ReviewDetailDto> reviews) {
        this.totalRateAvg = totalRateAvg;
        this.kindnessRateAvg = kindnessRateAvg;
        this.cleanlinessRateAvg = cleanlinessRateAvg;
        this.convenienceRateAvg = convenienceRateAvg;
        this.locationRateAvg = locationRateAvg;
        this.reviews = reviews;
    }
}
