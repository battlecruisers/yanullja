package com.battlecruisers.yanullja.room.dto;


import lombok.Data;

@Data
public class RoomNameDto {

    private Long id;

    private String name;

    public RoomNameDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
