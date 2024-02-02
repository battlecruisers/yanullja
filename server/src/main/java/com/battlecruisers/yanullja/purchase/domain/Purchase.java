package com.battlecruisers.yanullja.purchase.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Purchase extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 결제 가격
    private BigDecimal price;

    // 결제 상태 [취소, 완료, 진행중, 실패, 환불]??
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // 예약 Reservation
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    /**
     * 생성 메서드
     */
    protected Purchase(Long id) {
        this.id = id;
    }

    protected Purchase(
        Reservation reservation,
        BigDecimal price
    ) {
        this.reservation = reservation;
        this.price = price;
        this.paymentStatus = PaymentStatus.PROCESSING;
    }

    public static Purchase createPurchase(
        Reservation reservation,
        BigDecimal price
    ) {
        return new Purchase(reservation, price);
    }

}
