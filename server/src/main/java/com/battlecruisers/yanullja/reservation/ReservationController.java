package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.reservation.dto.ReservationCancelRequestDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationRequestDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationResponseDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationResultDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "결제 로직 조회")
    public ResponseEntity reservationsByMemberId(@RequestParam Long memberId) {
        Long mockMemberId = 1L;

        List<ReservationResultDto> results = reservationService.getReservationsByMemberId(
            memberId);
        return ResponseEntity.ok().body(results);
    }


    @PostMapping("/instant")
    @Operation(summary = "예약 및 결제")
    public ResponseEntity reserve(
        @RequestBody ReservationRequestDto reservationRequestDto) {
        log.info("ReservationController 호출 됨");
        log.info("ReservationController reservationRequestDto = {}",
            reservationRequestDto);
        //todo 스프링 시큐리티 멤버로직 추가
        Long mockMemberId = 1L;
        ReservationResponseDto reservationResponseDto = reservationService.reserve(
            reservationRequestDto, mockMemberId);

        return ResponseEntity.ok().body(reservationResponseDto);
    }

    @DeleteMapping
    @Operation(summary = "예약 및 결제 취소")
    public ResponseEntity cancel(
        @RequestBody ReservationCancelRequestDto cancelDto) {
        reservationService.cancel(cancelDto);

        return ResponseEntity.ok().build();
    }

}
