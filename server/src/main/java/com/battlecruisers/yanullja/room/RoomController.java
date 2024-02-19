package com.battlecruisers.yanullja.room;


import com.battlecruisers.yanullja.common.exception.CustomValidationException;
import com.battlecruisers.yanullja.common.jsendresponse.JSendResponse;
import com.battlecruisers.yanullja.room.domain.RoomType;
import com.battlecruisers.yanullja.room.dto.RoomDetailRequest;
import com.battlecruisers.yanullja.room.dto.RoomQueryDto;
import com.battlecruisers.yanullja.room.dto.RoomReservationInfoDto;
import com.battlecruisers.yanullja.room.example.RoomDetailOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "방", description = "숙소의 방 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;


    @RoomDetailOperation
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상 응답"),
        @ApiResponse(responseCode = "400", description = "검증 오류", content = @Content(schema = @Schema(implementation = JSendResponse.class))),
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomQueryDto> roomDetail(@PathVariable Long roomId, @Validated RoomDetailRequest parameter, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(
                bindingResult.getAllErrors().stream().findFirst().get()
                    .getDefaultMessage());
        }

        RoomQueryDto room = roomService.getRoom(roomId, parameter.getCheckInDate(), parameter.getCheckOutDate(), parameter.getRoomType());

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
