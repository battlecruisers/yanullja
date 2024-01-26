package com.battlecruisers.yanullja.reservation.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Reservation extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 Member
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean reservationStatus;

    // 방 Room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
