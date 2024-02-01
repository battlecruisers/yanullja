package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseRoomResponseDto {

    private Long cartProductId; // reservationId

    private Long roomOptionId; // roomId

    private String name; // roomName

    private String thumbnailImage; // roomImage

    private Long capacity; // roomCapacity

    private Long pricePerNight; //room 가격

    private Long totalPrice; //totalPrice

    private LocalDate reservationStartDate;

    private LocalDate reservationEndDate;

    private Integer stayDuration; // 디비에 없는 값

    public PurchaseRoomResponseDto(Purchase purchase, Room room) {
        Reservation reservation = purchase.getReservation();

        this.cartProductId = reservation.getId();
        this.roomOptionId = room.getId();
        this.name = room.getName();
//        this.thumbnailImage = room.getThumbnailImage();
        this.capacity = room.getCapacity().longValue();
        //todo 리액트 쪽 코드를 totalPrice로 바꿔도 괜찮을지 컨펌 받아보기.
        this.totalPrice = purchase.getPrice().longValue();
        this.reservationStartDate = reservation.getStartDate();
        this.reservationEndDate = reservation.getEndDate();
        this.stayDuration = reservationStartDate.until(reservationEndDate).getDays();
    }

    public static PurchaseRoomResponseDto createPurchaseRoomResponseDto(Purchase purchase, Room room) {
        return new PurchaseRoomResponseDto(purchase, room);
    }

}
