package com.battlecruisers.yanullja.purchase.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Purchase extends BaseDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가격 Long? Integer
    private Long price;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    // 무조건  Wrapper 클래스로? oo
    private Boolean paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    // 이용자 id
    // 이용자 연락처 추가?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
    // 룸 id

}
