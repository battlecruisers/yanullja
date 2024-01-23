package com.battlecruisers.yanullja.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceQueryDto {

    @Schema(name = "숙소 이름", example = "신라 호텔")
    private String name;

    @Schema(name = "숙소 총 평점", example = "4.7")
    private Double totalRate;

    @Schema(name = "리뷰 개수", example = "500")
    private Double reviewCount;

    @Schema(name = "대실 최소 가격", example = "25000", description = "해당 숙소에서 대실 가능한 모든 방 중 가장 저렴한 가격")
    private Integer rentMinPrice;

    @Schema(name = "숙박 최소 가격", example = "55000", description = "해당 숙소에서 숙박 가능한 모든 방 중 가장 저렴한 가격")
    private Integer stayMinPrice;

    @Schema(name = "한줄 이벤트 소개", example = "전 객실 대형TV")
    private String event;

}
