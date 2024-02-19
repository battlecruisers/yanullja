package com.battlecruisers.yanullja.room.dto;


import com.battlecruisers.yanullja.room.domain.RoomType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class RoomDetailRequest {

    @NotNull(message = "체크인 날짜가 없습니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate checkInDate;

    @NotNull(message = "체크아웃 날짜가 없습니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate checkOutDate;

    @NotNull(message = "방 유형이 없습니다.")
    RoomType roomType;
}
