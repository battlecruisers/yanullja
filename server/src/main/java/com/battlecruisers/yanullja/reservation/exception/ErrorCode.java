package com.battlecruisers.yanullja.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 발생 가능한 예외를 정리하는 Enum입니다.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_ENOUGH_ROOM_CAPACITY_EXCEPTION("해당 날짜에 방을 사용할 수 없습니다."),
    START_DATE_NOT_BEFORE_END_DATE_EXCEPTION("시작 날짜는 종료 날짜보다 이전이어야 합니다."),
    START_DATE_NOT_AFTER_TODAY_EXCEPTION("예약 시작 날짜는 오늘 날짜 이후여야 합니다.");

    private final String message; // 에러 메시지

}
