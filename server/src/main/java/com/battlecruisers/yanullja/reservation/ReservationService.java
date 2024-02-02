package com.battlecruisers.yanullja.reservation;

import com.battlecruisers.yanullja.member.MemberNotFoundException;
import com.battlecruisers.yanullja.member.MemberRepository;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.purchase.PurchaseRepository;
import com.battlecruisers.yanullja.purchase.PurchaseService;
import com.battlecruisers.yanullja.purchase.domain.Purchase;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import com.battlecruisers.yanullja.reservation.dto.ReservationRequestDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationResponseDto;
import com.battlecruisers.yanullja.reservation.dto.ReservationResultDto;
import com.battlecruisers.yanullja.reservation.exception.NotEnoughTotalRoomCountException;
import com.battlecruisers.yanullja.reservation.exception.ReservationNotFoundException;
import com.battlecruisers.yanullja.room.RoomRepository;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.exception.RoomNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final PurchaseService purchaseService;
    private final PurchaseRepository purchaseRepository;

    /**
     * 회원 아이디를 통해 예약정보를 조회합니다.
     *
     * @param memberId
     * @return
     */
    public List<ReservationResultDto> getReservationsByMemberId(Long memberId) {
        List<ReservationResultDto> results = new ArrayList<>();

        List<Purchase> purchases = purchaseRepository.findAllByMemberIdOrderByCreatedTimeDesc(
            memberId);

        for (Purchase purchase : purchases) {
            ReservationResultDto resultDto = ReservationResultDto.createReservationResultDto(
                purchase);
            results.add(resultDto);
        }

        return results;
    }

    /**
     * 예약 요청정보를 기반으로 예약을 진행합니다.
     *
     * @param requestDto 예약 요청 정보
     * @return ReservationResponseDto 결제완료 후 응답 관련 dto
     */
    @Transactional
    public ReservationResponseDto reserve(ReservationRequestDto requestDto,
        Long memberId) {
        Long roomId = requestDto.getRoomOptionId();
        Long memberCouponId = requestDto.getMemberCouponId();
        LocalDate startDate = requestDto.getReservationStartDate();
        LocalDate endDate = requestDto.getReservationEndDate();

        Member member = validateAndGetMember(memberId);

        Room room = validateAndGetRoom(roomId);

        // 해당 날짜에 방이 사용 가능한지 확인
        List<Reservation> reservations = reservationRepository.reservationsInDateRangeByRoomId(
            roomId, startDate, endDate);
        Integer remainingRoomCount =
            room.getTotalRoomCount() - getMaxAvailableRoomCount(reservations,
                room, startDate, endDate);

        if (remainingRoomCount < 0) {
            throw new NotEnoughTotalRoomCountException();
        }

        // 주문 생성
        Reservation reservation = Reservation.createReservation(member, room,
            startDate, endDate);
        reservationRepository.save(reservation);

        // purchase 진행 (쿠폰 계산로직 포함)
        Purchase purchase = purchaseService.purchase(reservation,
            memberCouponId);

        return ReservationResponseDto.createReservationResponseDto(reservation,
            purchase);
    }

    private Room validateAndGetRoom(Long roomId) {
        return roomRepository.findById(roomId)
            .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    private Member validateAndGetMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    /**
     * 특정 방(room)을 기준으로 날짜 범위 내 예약의 최댓값을 반환합니다.
     *
     * @param reservations 확인할 예약 목록
     * @param room         예약을 확인할 방 번호
     * @param startDate    예약 시작 날짜 (포함)
     * @param endDate      예약 종료 날짜 (불포함)
     * @return 조회된 기간내 사용가능한 객실 수의 최댓값
     */
    public Integer getMaxAvailableRoomCount(List<Reservation> reservations,
        Room room, LocalDate startDate, LocalDate endDate) {

        // 예약이 없는 경우, 전체 객실 수 반환
        if (reservations == null || reservations.isEmpty()) {
            return room.getTotalRoomCount();
        }

        // 날짜 별 예약 수 확인
        List<Integer> reservationCounts = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate);
            date = date.plusDays(1)) {
            LocalDate currentDate = date;
            long reservationCountForDate = reservations.stream()
                .filter(reservation -> reservation.isDateWithinReservation(
                    currentDate))
                .count();

            reservationCounts.add((int) reservationCountForDate);
        }

        // 사용 가능한 객실 수의 최소값 반환
        return Collections.max(reservationCounts);
    }

    /**
     * 예약 취소를 진행합니다.
     *
     * @param reservationId 최소 요청할 예약 식별자
     * @throws ReservationNotFoundException 주어진 예약 ID에 해당하는 예약이 존재하지 않을 경우 예외가 발생합니다.
     */
    @Transactional
    public void cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);


        reservation.cancel();
    }

}
