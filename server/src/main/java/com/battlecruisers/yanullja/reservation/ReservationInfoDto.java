package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfoDto {

    private Long placeId;
    private String placeName;
    private String address;
    private String placeThumbnailImageUrl;

    private Long roomId;
    private String roomName;
    private Integer capacity;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal price;

    public static ReservationInfoDto createReservationInfoDto(Purchase purchase) {
        Reservation reservation = purchase.getReservation();
        Room room = reservation.getRoom();
        Place place = room.getPlace();

        return new ReservationInfoDto(place.getId(), place.getName(), place.getAddress(), place.getThumbnailImageUrl(),
                room.getId(), room.getName(), room.getCapacity(), reservation.getStartDate(), reservation.getEndDate(), purchase.getPrice());
    }
}
