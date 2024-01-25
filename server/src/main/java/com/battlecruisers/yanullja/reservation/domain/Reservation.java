package com.battlecruisers.yanullja.reservation.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.reservation.exception.StartDateNotAfterTodayException;
import com.battlecruisers.yanullja.reservation.exception.StartDateNotBeforeEndDateException;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예약 시작 날짜 (해당일 포함)
    private LocalDate startDate;

    // 예약 종료 날짜 (해당일 포함)
    private LocalDate endDate;

    // 예약 상태 [RESERVE, CANCEL]
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    // 회원 Member
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 방 Room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * 생성 메서드
     **/
    protected Reservation(Long roomId) {
        this.id = roomId;
    }

    protected Reservation(
            Member member,
            Room room,
            LocalDate startDate,
            LocalDate endDate
    ) {
        validateStartBeforeEnd(startDate, endDate);
        validateStartAfterToday(startDate);

        this.member = member;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationStatus = ReservationStatus.RESERVE;
    }

    public static Reservation createReservation(
            Member member,
            Room room,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return new Reservation(member, room, startDate, endDate);
    }

    private void validateStartBeforeEnd(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateNotBeforeEndDateException();
        }
    }

    private void validateStartAfterToday(LocalDate startDate) {
        if (startDate.isBefore(startDate)) {
            throw new StartDateNotAfterTodayException();
        }
    }

    /**
     * 비즈니스 로직
     **/
    public void cancel() {
        this.reservationStatus = ReservationStatus.CANCEL; // 주문 취소
    }

    /**
     * 특정 날짜가 예약 기간 내에 있는지 확인하는 메서드 입니다.
     *
     * @param checkDate 확인할 날짜
     * @return 예약 기간 내에 있으면 true, 그렇지 않으면 false
     */
    public boolean isDateWithinReservation(LocalDate checkDate) {
        return (checkDate.isAfter(startDate) && checkDate.isBefore(endDate)) ||
                checkDate.isEqual(startDate) || checkDate.isEqual(endDate);
    }

}
