package com.battlecruisers.yanullja.reservation.domain;

public enum ReservationStatus {
    RESERVE, CANCEL;

    public static boolean isCancel(ReservationStatus reservationStatus) {
        if (reservationStatus == CANCEL) {
            return true;
        }
        return false;
    }
}
