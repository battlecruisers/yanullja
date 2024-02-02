package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.battlecruisers.yanullja.place.domain.QPlace.place;
import static com.battlecruisers.yanullja.purchase.domain.QPurchase.purchase;
import static com.battlecruisers.yanullja.reservation.domain.QReservation.reservation;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;

@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Reservation> queryReservationsInDateRange(List<Place> placeList, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.selectFrom(reservation)
                .leftJoin(reservation.room, room).fetchJoin()
                .leftJoin(room.place, place).fetchJoin()
                .where(reservation.room.place.in(placeList))
                .where(reservation.endDate.after(startDate))
                .where(reservation.startDate.before(endDate))
                .fetch();
    }

    @Override
    public Optional<Purchase> queryReservation(Long reservationId) {
        Purchase findPurchase = jpaQueryFactory.selectFrom(purchase)
                .leftJoin(purchase.reservation, reservation).fetchJoin()
                .leftJoin(reservation.room, room).fetchJoin()
                .leftJoin(room.place, place).fetchJoin()
                .where(reservation.id.eq(reservationId))
                .fetchOne();
        return Optional.ofNullable(findPurchase);
    }
}
