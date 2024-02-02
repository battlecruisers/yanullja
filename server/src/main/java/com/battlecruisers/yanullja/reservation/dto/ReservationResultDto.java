package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.reservation.domain.ReservationStatus;
import com.battlecruisers.yanullja.room.domain.Room;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ReservationResultDto {

    private Long paymentId; // reservationId
    private Boolean paymentCanceled; // 예약 취소 여부(reservationStatus)
    private String reservationNumber; // reserveNumber

    private List<ReservationResultPlaceDto> accommodations;

    protected ReservationResultDto(Reservation reservation) {
        this.paymentId = reservation.getId();
        this.paymentCanceled = ReservationStatus.isCancel(reservation.getReservationStatus());
        this.reservationNumber = reservation.getReserveNumber();
        this.accommodations = new ArrayList<>();
    }

    public static ReservationResultDto createReservationResultDto(
        Purchase purchase) {
        Reservation reservation = purchase.getReservation();
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        Room room = reservation.getRoom();
        Place place = room.getPlace();

        // 1. DTO: Room 설정
        ReservationResultRoomDto reservationResultRoomDto =
            ReservationResultRoomDto.createReservationRoomResponseDto(purchase);

        // 2. DTO: Place 설정 (1의 Room 정보 추가)
        ReservationResultPlaceDto reservationResultPlaceDto =
            ReservationResultPlaceDto.createReservationResultPlaceDto(place);
        reservationResultPlaceDto.getRoomOptions()
            .add(reservationResultRoomDto);

        // 3. DTO: Reserve 설정 (2의 Place 정보 추가)
        ReservationResultDto reservationResultDto =
            new ReservationResultDto(reservation);
        reservationResultDto.getAccommodations().add(reservationResultPlaceDto);

        return reservationResultDto;
    }


}
