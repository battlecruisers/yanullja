package com.battlecruisers.yanullja.purchase.dto;

import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.purchase.domain.PayType;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.Data;

@Data
public class PurchaseDto {

    private Long id;
    private Long price;
    private PayType payType;
    private Boolean paymentStatus;

    // 회원정보
    private Member member;

    // 룸 정보
    private Room room;

}
