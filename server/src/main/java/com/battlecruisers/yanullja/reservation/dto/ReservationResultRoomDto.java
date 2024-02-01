package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.room.domain.Room;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ReservationResultRoomDto {

    private Long paymentProductId; // reservationId

    //todo null 이어도 괜찮을지 프론트 확인하기.
    //todo 널이어도 괜찮을지 확인 받기.
    private Long accommodationId; // place Id (null)

    private Long roomOptionId; // room Id

    private String name; // room Name

    private String thumbnailImage; // room thumbnailImage 아직 없음

    private Long capacity; // room capacity

    private Long pricePerNight; // room 가격

    private Long totalPrice; // totalPrice

    private LocalDate reservationStartDate;

    private LocalDate reservationEndDate;

    private Integer stayDuration;

    // private Long numberOfGuest; // 수용인원 중복?

    public ReservationResultRoomDto(Purchase purchase) {
        Reservation reservation = purchase.getReservation();
        Room room = reservation.getRoom();

        this.paymentProductId = reservation.getId();
        this.roomOptionId = room.getId();
        this.name = room.getName();
        this.capacity = room.getCapacity().longValue();
        this.totalPrice = purchase.getPrice().longValue();
        this.reservationStartDate = reservation.getStartDate();
        this.reservationEndDate = reservation.getEndDate();
        this.stayDuration = reservationStartDate.until(reservationEndDate)
            .getDays();
    }

    public static ReservationResultRoomDto createReservationRoomResponseDto(
        Purchase purchase) {
        return new ReservationResultRoomDto(purchase);
    }

}
