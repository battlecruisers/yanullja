package com.battlecruisers.yanullja.room.dto;


import com.battlecruisers.yanullja.place.dto.RoomOptionImageDto;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RoomDetailDto {

    private Long id;

    private String name;

    private Integer capacity;

    private LocalTime rentTime;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer price;

    private Integer leftRoomCount;

    private RoomOptionImageDto roomOptionImage;


}
