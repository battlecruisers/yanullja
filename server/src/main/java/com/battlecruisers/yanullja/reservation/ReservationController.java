package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.member.domain.SecurityMember;
import com.battlecruisers.yanullja.reservation.dto.ReservationCancelRequestDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationRequestDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationResponseDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationResultDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "결제 로직 조회")
    public ResponseEntity<List<ReservationResultDto>> reservationsByMemberId(
            @AuthenticationPrincipal SecurityMember me
    ) {
        var memberId = me.getId();

        List<ReservationResultDto> results = reservationService.getReservationsByMemberId(
                memberId);
        return ResponseEntity.ok().body(results);
    }


    @PostMapping("/instant")
    @Operation(summary = "예약 및 결제")
    public ResponseEntity<ReservationResponseDto> reserve(
            @RequestBody ReservationRequestDto reservationRequestDto,
            @AuthenticationPrincipal SecurityMember me
    ) {
        log.info("ReservationController 호출 됨");
        //todo 스프링 시큐리티 멤버로직 추가
        Long mockMemberId = me.getId();
        ReservationResponseDto reservationResponseDto = reservationService.reserve(
                reservationRequestDto, mockMemberId);

        return ResponseEntity.ok().body(reservationResponseDto);
    }

    @DeleteMapping
    @Operation(summary = "예약 및 결제 취소")
    public ResponseEntity<Void> cancel(
            @RequestBody ReservationCancelRequestDto cancelDto) {
        reservationService.cancel(cancelDto.getPaymentId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("{reservationId}/info")
    public ResponseEntity getReservationInfo(@PathVariable("reservationId") Long reservationId) {
        ReservationInfoDto reservationResultDto = reservationService.getReservationInfo(
                reservationId);
        return ResponseEntity.ok().body(reservationResultDto);

    }

}
