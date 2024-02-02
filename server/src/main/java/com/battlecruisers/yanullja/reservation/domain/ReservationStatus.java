package com.battlecruisers.yanullja.reservation.domain;

public enum ReservationStatus {
    RESERVE, CANCEL;

    public boolean isCancel() {
        return this == CANCEL;
    }
}
