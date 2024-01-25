package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.reservation.exception.NotEnoughRoomCapacityException;
import com.battlecruisers.yanullja.room.RoomRepository;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    /**
     * 회원정보, 방정보를 토대로 예약 정보를 저장합니다.
     *
     * @param memberId 회원 식별자
     * @param roomId   방 식별자
     * @return 예약 식별자
     */
    @Transactional
    public Long reserve(Long memberId, Long roomId, LocalDate startDate, LocalDate endDate) {
        Member member = new Member(memberId);
        Room room = new Room(roomId);

        // 해당 날짜에 방이 사용 가능한지 확인
        validateRoomAffordable(roomId, startDate, endDate);

        // 주문 생성
        Reservation reservation = Reservation.createReservation(member, room, startDate, endDate);
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    /**
     * 주어진 날짜 범위에 예약 가능한 방의 숫자를 확인합니다.
     *
     * @param roomId    확인할 방 번호
     * @param startDate 예약 시작 날짜
     * @param endDate   예약 종료 날짜
     * @throws NotEnoughRoomCapacityException 방의 수용 가능 인원을 초과하는 경우 발생하는 예외
     */
    private void validateRoomAffordable(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.reservationsInDateRange(roomId, startDate, endDate);
        Room room = roomRepository.findById(roomId).orElseThrow();

        for (Reservation r : reservations) {
            log.info("reservation = {}", r);
        }

        // 날짜 별 예약 수 확인
        for (LocalDate date = startDate; date.isEqual(endDate) || date.isBefore(endDate); date = date.plusDays(1)) {
            LocalDate currentDate = date;
            long reservationCountForDate = reservations.stream()
                    .filter(reservation -> reservation.isDateWithinReservation(currentDate))
                    .count();

            log.info("currentDate = {}", currentDate);
            log.info("reservationCountForDate = {}", reservationCountForDate);
            log.info("room.capacity = {}", room.getCapacity());

            if (reservationCountForDate >= room.getCapacity()) {
                throw new NotEnoughRoomCapacityException();
            }
        }

    }

    /**
     * 주문취소
     **/
    @Transactional
    public void cancel(Long reservationId) {
        // 예약 엔티티 조회
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(IllegalArgumentException::new);

        // 예약 취소
        reservation.cancel();
    }


}
