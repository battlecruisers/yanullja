package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ReservationResponseDto {

    private Long cartId; // reservationId
    private Long count = 1L; // 예약상품 갯수: 1 (항상 단일 구매만 가능)
    private List<ReservationResponsePlaceDto> accommodations; // place목록

    protected ReservationResponseDto(Reservation reservation) {
        this.cartId = reservation.getId();
        this.accommodations = new ArrayList<>();
    }

    public static ReservationResponseDto createReservationResponseDto(
        Reservation reservation, Purchase purchase) {
        // 1. DTO: Room 설정
        ReservationResponseRoomDto reservationResponseRoomDto =
            ReservationResponseRoomDto.createReservationRoomResponseDto(
                purchase, reservation.getRoom());

        // 2. DTO: Place 설정 (1의 Room 정보 추가)
        ReservationResponsePlaceDto reservationResponsePlaceDto =
            ReservationResponsePlaceDto.createPurchasePlaceResponseDto(
                reservation.getRoom().getPlace());
        reservationResponsePlaceDto.getRoomOptions()
            .add(reservationResponseRoomDto);

        // 3. DTO: Reserve 설정 (2의 Place 정보 추가)
        ReservationResponseDto reservationResponseDto =
            new ReservationResponseDto(reservation);
        reservationResponseDto.getAccommodations()
            .add(reservationResponsePlaceDto);

        return reservationResponseDto;
    }

}
