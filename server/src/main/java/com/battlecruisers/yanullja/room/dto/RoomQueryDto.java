package com.battlecruisers.yanullja.room.dto;

import static com.battlecruisers.yanullja.place.PlaceService.findMaxDiscountPrice;
import static com.battlecruisers.yanullja.place.PlaceService.getWeekDayCount;

import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.domain.RoomImage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomQueryDto {

    private Long id;

    private Long accommodationId;

    @Schema(name = "객실 이름", example = "스탠다드룸")
    private String name;

    @Schema(name = "객실 대표 사진 리스트", description = "객실 대표 사진의 저장 url을 리턴", example = "https://aws1.s3.ap-northeast-2.amazonaws.com/room/room1.jpg")
    private List<String> roomOptionImage;

    @Schema(name = "객실 최대 인원", example = "4")
    private Integer capacity;

    private Integer totalRoomCount;

    private Integer reservedRoomCount;

    private String description;

    @Schema(name = "숙박 체크인 시간", example = "1800")
    private LocalTime checkInTime;

    @Schema(name = "숙박 체크아웃 시간", example = "2100")
    private LocalTime checkOutTime;

    @Schema(name = "숙박 가격", example = "12000")
    private Integer totalPrice;

    private Integer stayDuration;

    @Schema(name = "숙박에 적용 가능한 최고 할인가", example = "3000")
    private Integer stayMaxDiscount;


    public static RoomQueryDto from(Room room, LocalDate checkInDate, LocalDate checkOutDate,
        Integer reservedRoomCount) {

        Long days = (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
        Integer weekDayCount = getWeekDayCount(checkInDate, checkOutDate);
        Integer weekendCount = days.intValue() - weekDayCount;

        return new RoomQueryDto(
            room.getId(),
            room.getPlace().getId(),
            room.getName(),
            room.getRoomImages().stream().map(RoomImage::getImageUrl)
                .collect(Collectors.toList()),
            room.getCapacity(),
            room.getTotalRoomCount(),
            reservedRoomCount,
            "example description",
            room.choiceCheckInTime(checkInDate),
            room.choiceCheckOutTime(checkOutDate),
            room.calcTotalPrice(weekDayCount, weekendCount),
            days.intValue(),
            findMaxDiscountPrice(room, checkInDate, RoomType.Stay)
        );

    }

    /**
     * 대실에 대한 DTO 칼럼 제거
     */
    //    @Schema(name = "대실 시작 시간", example = "1000")
//    private Integer rentStartTime;
//
//    @Schema(name = "대실 종료 시간", example = "1300")
//    private Integer rentEndTime;
//
//    @Schema(name = "대실 시간", example = "4")
//    private Integer rentTime;
//
//    @Schema(name = "대실 가격", example = "12000")
//    private Integer rentPrice;

    //    @Schema(name = "대실에 적용 가능한 최고 할인가", example = "3000")
//    private Integer rentMaxDiscount;


}
