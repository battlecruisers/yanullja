package com.battlecruisers.yanullja.room;


import com.battlecruisers.yanullja.room.domain.RoomType;
import com.battlecruisers.yanullja.room.dto.RoomQueryDto;
import com.battlecruisers.yanullja.room.dto.RoomReservationInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;


    @GetMapping("/{roomId}")
    public ResponseEntity<RoomQueryDto> roomDetail(@PathVariable Long roomId,
                                                   @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                   @RequestParam("roomType") RoomType roomType) {

        RoomQueryDto room = roomService.getRoom(roomId, checkInDate, checkOutDate, roomType);

        return ResponseEntity
            .ok()
            .body(room);
    }

    @Operation(summary = "예약에 필요한 방 정보 조회")
    @GetMapping("/{roomId}/reservation")
    public ResponseEntity<RoomReservationInfoDto> queryRoomDetailForReservation(@PathVariable Long roomId) {

        RoomReservationInfoDto roomReservationInfoDto = roomService.queryRoomDetailForReservation(roomId);

        return ResponseEntity
                .ok()
                .body(roomReservationInfoDto);
    }

}
