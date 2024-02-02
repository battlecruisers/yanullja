package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, CustomReservationRepository {

    @Query("SELECT r " +
            "FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.reservationStatus = 'RESERVE' " +
            "AND (:newStartDate < r.endDate AND :newEndDate > r.startDate)")
    List<Reservation> reservationsInDateRangeByRoomId(@Param("roomId") Long roomId,
                                                      @Param("newStartDate") LocalDate newStartDate,
                                                      @Param("newEndDate") LocalDate newEndDate);

    List<Reservation> findByMemberId(Long memberId);

}
