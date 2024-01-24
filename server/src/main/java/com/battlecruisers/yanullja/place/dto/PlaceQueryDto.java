package com.battlecruisers.yanullja.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceQueryDto {

    @Schema(name = "숙소 id")
    private Long id;

    @Schema(name = "숙소 이름", example = "신라 호텔")
    private String name;

    @Schema(name = "숙소 대표 사진", example = "https://aws1.s3.ap-northeast-2.amazonaws.com/place/place1.jpg", description = "숙소 대표 사진의 url 받음")
    private String thumbnailImageUrl;

    @Schema(name = "대실 최소 가격", example = "25000", description = "해당 숙소에서 대실 가능한 모든 방 중 가장 저렴한 가격")
    //private Integer rentMinPrice;
    private Integer minimumPrice;

    @Schema(name = "숙박 최소 가격", example = "55000", description = "해당 숙소에서 숙박 가능한 모든 방 중 가장 저렴한 가격")
    //private Integer stayMinPrice;
    private Integer maximumPrice;

    @Schema(name = "한줄 이벤트 소개", example = "전 객실 대형TV")
    private String event;

    @Schema(name = "카테고리")
    private String category;

}
