package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.Data;

@Data
public class ReservationDto {

    private Long id;

    // 회원 정보
    private Member member;

    // 예약 상태
    private Boolean reservationStatus;

    // 룸 정보
    private Room room;

}
