package com.battlecruisers.yanullja.review.dto;


import lombok.Data;

@Data
public class ReviewSaveDto {

    private Long placeId;

    private Long roomId;

    private Long memberId = 1L;

    private String content;

    private Double kindnessRate;

    private Double cleanlinessRate;

    private Double convenienceRate;

    private Double locationRate;

    private Double totalRate;
}
