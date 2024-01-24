package com.battlecruisers.yanullja.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomListQueryDto {

    @Schema(name = "객실 대표 사진 리스트", description = "객실 대표 사진의 저장 url을 리턴", example = "https://aws1.s3.ap-northeast-2.amazonaws.com/room/room1.jpg")
    private List<String> roomImageList;

    @Schema(name = "객실 이름", example = "스탠다드룸")
    private String roomName;

    @Schema(name = "객실 최대 인원", example = "4")
    private Integer capacity;

    @Schema(name = "대실 시작 시간", example = "1000")
    private Integer rentStartTime;

    @Schema(name = "대실 종료 시간", example = "1300")
    private Integer rentEndTime;

    @Schema(name = "대실 시간", example = "4")
    private Integer rentTime;

    @Schema(name = "대실 가격", example = "12000")
    private Integer rentPrice;

    @Schema(name = "숙박 체크인 시간", example = "1800")
    private Integer checkInTime;

    @Schema(name = "숙박 체크아웃 시간", example = "2100")
    private Integer checkOutTime;

    @Schema(name = "숙박 가격", example = "12000")
    private Integer stayPrice;

    @Schema(name = "대실에 적용 가능한 최고 할인가", example = "3000")
    private Integer rentMaxDiscount;

    @Schema(name = "숙박에 적용 가능한 최고 할인가", example = "3000")
    private Integer stayMaxDiscount;


}
