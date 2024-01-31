package com.battlecruisers.yanullja.reservation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationResponseDto {

    private Long cartId; // reservationId
    private Long count = 1L; // 예약상품 갯수: 1 (항상 단일 구매만 가능)
    private List<PurchasePlaceResponseDto> accommodations; // place목록

    protected ReservationResponseDto(Long reservationId) {
        this.cartId = reservationId;
        this.accommodations = new ArrayList<>();
    }

    public static ReservationResponseDto createReservationResponseDto(Long reservationId) {
        return new ReservationResponseDto(reservationId);
    }

}
