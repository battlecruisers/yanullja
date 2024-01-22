package com.battlecruisers.yanullja.room.dto;


import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.domain.RoomImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class RoomDto {

    private Long id;

    private String name;

    private String category;

    private Integer capacity;

    private Integer weekdayRentTime;

    private Integer weekdayRentStartTime;

    private Integer weekdayRentEndTime;

    private Integer weekdayCheckInTime;

    private Integer weekdayCheckOutTime;

    private Integer weekdayRentPrice;

    private Integer weekdayStayPrice;

    private Integer weekendRentTime;

    private Integer weekendRentStartTime;

    private Integer weekendRentEndTime;

    private Integer weekendCheckInTime;

    private Integer weekendCheckOutTime;

    private Integer weekendRentPrice;

    private Integer weekendStayPrice;

    private List<String> urls;

    public RoomDto(Long id, String name, String category, Integer capacity, Integer weekdayRentTime,
        Integer weekdayRentStartTime,
        Integer weekdayRentEndTime, Integer weekdayCheckInTime, Integer weekdayCheckOutTime,
        Integer weekdayRentPrice,
        Integer weekdayStayPrice, Integer weekendRentTime, Integer weekendRentStartTime,
        Integer weekendRentEndTime,
        Integer weekendCheckInTime, Integer weekendCheckOutTime, Integer weekendRentPrice,
        Integer weekendStayPrice, List<String> urls) {
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
        this.urls = urls;
    }

    public static RoomDto createNewRoomDto(Room room) {
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
            room.getWeekendRentPrice(),
            room.getWeekendStayPrice(),
            room.getRoomImages().stream().map(RoomImage::getImageUrl).collect(Collectors.toList())
        );
    }
}
