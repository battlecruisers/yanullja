package com.battlecruisers.yanullja.room.dto;


import com.battlecruisers.yanullja.place.dto.RoomOptionImageDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.domain.RoomImage;
import lombok.Data;

import java.time.LocalTime;
import java.util.stream.Collectors;

@Data
public class RoomDto {

    private Long id;

    private String name;

    private String category;

    private Integer capacity;

    private LocalTime weekdayRentTime;

    private LocalTime weekdayRentStartTime;

    private LocalTime weekdayRentEndTime;

    private LocalTime weekdayCheckInTime;

    private LocalTime weekdayCheckOutTime;

    private Integer weekdayRentPrice;

    private Integer weekdayStayPrice;

    private LocalTime weekendRentTime;

    private LocalTime weekendRentStartTime;

    private LocalTime weekendRentEndTime;

    private LocalTime weekendCheckInTime;

    private LocalTime weekendCheckOutTime;

    private Integer weekendRentPrice;

    private Integer weekendStayPrice;

    private RoomOptionImageDto roomOptionImage;

    public RoomDto(Long id, String name, String category, Integer capacity, LocalTime weekdayRentTime, LocalTime weekdayRentStartTime, LocalTime weekdayRentEndTime,
                   LocalTime weekdayCheckInTime, LocalTime weekdayCheckOutTime, Integer weekdayRentPrice, Integer weekdayStayPrice, LocalTime weekendRentTime,
                   LocalTime weekendRentStartTime, LocalTime weekendRentEndTime, LocalTime weekendCheckInTime, LocalTime weekendCheckOutTime, Integer weekendRentPrice,
                   Integer weekendStayPrice, RoomOptionImageDto roomOptionImage) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.weekdayRentTime = weekdayRentTime;
        this.weekdayRentStartTime = weekdayRentStartTime;
        this.weekdayRentEndTime = weekdayRentEndTime;
        this.weekdayCheckInTime = weekdayCheckInTime;
        this.weekdayCheckOutTime = weekdayCheckOutTime;
        this.weekdayRentPrice = weekdayRentPrice;
        this.weekdayStayPrice = weekdayStayPrice;
        this.weekendRentTime = weekendRentTime;
        this.weekendRentStartTime = weekendRentStartTime;
        this.weekendRentEndTime = weekendRentEndTime;
        this.weekendCheckInTime = weekendCheckInTime;
        this.weekendCheckOutTime = weekendCheckOutTime;
        this.weekendRentPrice = weekendRentPrice;
        this.weekendStayPrice = weekendStayPrice;
        this.roomOptionImage = roomOptionImage;
    }

    public static RoomDto from(Room room) {
        return new RoomDto(
                room.getId(),
                room.getName(),
                room.getCategory(),
                room.getCapacity(),
                room.getWeekdayRentTime(),
                room.getWeekdayRentStartTime(),
                room.getWeekdayRentEndTime(),
                room.getWeekdayCheckInTime(),
                room.getWeekdayCheckOutTime(),
                room.getWeekdayRentPrice(),
                room.getWeekdayStayPrice(),
                room.getWeekendRentTime(),
                room.getWeekendRentStartTime(),
                room.getWeekendRentEndTime(),
                room.getWeekendCheckInTime(),
                room.getWeekendCheckOutTime(),
                room.getWeekendRentPrice(), room.getWeekendStayPrice(),
                new RoomOptionImageDto(room.getRoomImages().stream().map(RoomImage::getImageUrl)
                        .collect(Collectors.toList()))
        );
    }
}
