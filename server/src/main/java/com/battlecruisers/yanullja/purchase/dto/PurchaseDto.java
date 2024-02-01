package com.battlecruisers.yanullja.purchase.dto;

import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseDto {

    private Long id;
    private BigDecimal price;
    private Boolean paymentStatus;

    // 회원정보
    private Member member;

    // 룸 정보
    private Room room;

}
