package com.battlecruisers.yanullja.room.dto;


import com.battlecruisers.yanullja.place.dto.RoomOptionImageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RoomDetailDto {

    @Schema(description = "방 ID")
    private Long id;

    @Schema(description = "방 이름")
    private String name;

    @Schema(description = "최대 수용 인원")
    private Integer capacity;

    @Schema(description = "대실 시간")
    private LocalTime rentTime;

    @Schema(description = "대실, 숙박 체크인 시간")
    private LocalTime startTime;

    @Schema(description = "대실, 숙박 체크아웃 시간")
    private LocalTime endTime;

    @Schema(description = "가격")
    private Integer price;

    @Schema(description = "남은 방 수")
    private Integer leftRoomCount;

    @Schema(description = "방 이미지")
    private RoomOptionImageDto roomOptionImage;


}
