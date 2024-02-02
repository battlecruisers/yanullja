package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomReservationRepository {

    List<Reservation> queryReservationsInDateRange(List<Place> placeList, LocalDate startDate, LocalDate endDate);

    Optional<Purchase> queryReservation(Long reservationId);
}
