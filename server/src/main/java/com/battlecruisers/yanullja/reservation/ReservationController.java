package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.member.MemberService;
import com.battlecruisers.yanullja.reservation.dto.ReservationRequestDto;
import com.battlecruisers.yanullja.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private final RoomService roomService;

    @PostMapping("/reservation")
    public ResponseEntity reserve(@ModelAttribute("reservationRequestDto") ReservationRequestDto reservationRequestDto) {
        log.info("ReservationController 호출 됨");
        log.info("ReservationController reservationRequestDto = {}", reservationRequestDto);

        Long reservationId = reservationService.reserve(reservationRequestDto);

        return ResponseEntity.ok().body(reservationId);
    }

}
