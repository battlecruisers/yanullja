package com.battlecruisers.yanullja.review.dto;


import java.util.List;
import lombok.Data;

@Data
public class ReviewStatisticsDto {

    private Long totalCount;
    private Double totalRateAvg;
    private Double kindnessRateAvg;
    private Double cleanlinessRateAvg;
    private Double convenienceRateAvg;
    private Double locationRateAvg;
    private List<ReviewDetailDto> reviews;


    public ReviewStatisticsDto(Long totalCount, Double totalRateAvg,
        Double kindnessRateAvg, Double cleanlinessRateAvg,
        Double convenienceRateAvg, Double locationRateAvg,
        List<ReviewDetailDto> reviews) {
        this.totalCount = totalCount;
        this.totalRateAvg = totalRateAvg;
        this.kindnessRateAvg = kindnessRateAvg;
        this.cleanlinessRateAvg = cleanlinessRateAvg;
        this.convenienceRateAvg = convenienceRateAvg;
        this.locationRateAvg = locationRateAvg;
        this.reviews = reviews;
    }
}
