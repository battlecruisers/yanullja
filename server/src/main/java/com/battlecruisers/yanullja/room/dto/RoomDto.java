package com.battlecruisers.yanullja.room.dto;


import java.util.List;
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

//    public static from(Room room) {
//        return new RoomDto(room.getId(), room.getName(), room.getCategory(), room.getCapacity(),
//            room.getWeekdayRentTime(), room.getWeekdayRentStartTime(), room.getWeekdayRentEndTime(),
//            room.getWeekdayCheckInTime(), room.getWeekdayCheckOutTime(), room.getWeekdayRentPrice(),
//            room.getWeekdayStayPrice(), room.getWeekendRentTime(), room.getWeekendRentStartTime(),
//            room.getWeekendRentEndTime(), room.getWeekendCheckInTime(),
//            room.getWeekendCheckOutTime(),
//            room.getWeekendRentPrice(), room.getWeekendStayPrice(), room.getUrls());
//    }
}
