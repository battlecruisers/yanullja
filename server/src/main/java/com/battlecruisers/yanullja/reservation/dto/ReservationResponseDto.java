package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
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

    public static ReservationResponseDto buildReservationResponseDto(Reservation reservation, Purchase purchase) {
        // DTO 설정
        // 1. DTO: Room 설정
        PurchaseRoomResponseDto purchaseRoomResponseDto =
                PurchaseRoomResponseDto.createPurchaseRoomResponseDto(purchase, reservation.getRoom());

        // 2. DTO: Place 설정
        PurchasePlaceResponseDto purchasePlaceResponseDto =
                PurchasePlaceResponseDto.createPurchasePlaceResponseDto(reservation.getRoom().getPlace());
        purchasePlaceResponseDto.getRoomOptions().add(purchaseRoomResponseDto);

        // 3. DTO: Reserve 설정
        ReservationResponseDto reservationResponseDto =
                ReservationResponseDto.createReservationResponseDto(reservation.getId());
        reservationResponseDto.getAccommodations().add(purchasePlaceResponseDto);

        return reservationResponseDto;
    }

}
