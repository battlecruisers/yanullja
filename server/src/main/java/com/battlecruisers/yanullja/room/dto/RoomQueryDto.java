package com.battlecruisers.yanullja.room.dto;

import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.place.dto.RoomOptionImageDto;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.domain.RoomImage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.battlecruisers.yanullja.place.PlaceService.findMaxDiscountPrice;

@Getter
@Setter
@AllArgsConstructor
public class RoomQueryDto {

    private Long id;

    private Long accommodationId;

    @Schema(name = "객실 이름", example = "스탠다드룸")
    private String name;

    @Schema(name = "객실 대표 사진 리스트", description = "객실 대표 사진의 저장 url을 리턴", example = "https://aws1.s3.ap-northeast-2.amazonaws.com/room/room1.jpg")
    private RoomOptionImageDto roomOptionImage;

    @Schema(name = "객실 최대 인원", example = "4")
    private Integer capacity;

    private Integer totalRoomCount;

    private Integer reservedRoomCount;

    @Schema(name = "숙박 체크인/대실 시작 시간", example = "1800")
    private LocalTime startTime;

    @Schema(name = "숙박 체크아웃/대실 마감 시간", example = "2100")
    private LocalTime endTime;

    @Schema(name = "숙박 가격", example = "12000")
    private BigDecimal totalPrice;

    private Integer stayDuration;

    @Schema(name = "숙박에 적용 가능한 최고 할인가", example = "3000")
    private Integer stayMaxDiscount;


    public static RoomQueryDto from(Room room, LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer reservedRoomCount) {

        Long days = checkOutDate.toEpochDay() - checkInDate.toEpochDay();

        return new RoomQueryDto(
                room.getId(),
                room.getPlace().getId(),
                room.getName(),
                new RoomOptionImageDto(room.getRoomImages().stream().map(RoomImage::getImageUrl)
                        .collect(Collectors.toList())),
                room.getCapacity(),
                room.getTotalRoomCount(),
                reservedRoomCount,
                room.choiceCheckInTime(checkInDate),
                room.choiceCheckOutTime(checkOutDate),
                room.calcTotalPrice(checkInDate, checkOutDate),
                days.intValue(),
                findMaxDiscountPrice(room, checkInDate, RoomType.STAY)
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
