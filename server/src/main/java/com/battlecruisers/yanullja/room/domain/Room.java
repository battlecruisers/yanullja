package com.battlecruisers.yanullja.room.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.battlecruisers.yanullja.place.PlaceService.getWeekDayCount;
import static com.battlecruisers.yanullja.place.PlaceService.isWeekend;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseDate {

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private final List<Coupon> coupons = new ArrayList<>();
    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<RoomImage> roomImages = new ArrayList<>();
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private final List<Reservation> reservations = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Integer capacity;
    private LocalTime weekdayRentTime;
    private LocalTime weekdayRentStartTime;
    private LocalTime weekdayRentEndTime;
    private LocalTime weekdayCheckInTime;
    private LocalTime weekdayCheckOutTime;
    private Integer weekdayRentPrice;
    private Integer weekdayStayPrice;

    private LocalTime weekendRentTime;
    private LocalTime weekendRentStartTime;
    private LocalTime weekendRentEndTime;
    private LocalTime weekendCheckInTime;
    private LocalTime weekendCheckOutTime;
    private Integer weekendRentPrice;
    private Integer weekendStayPrice;

    private Integer totalRoomCount;
    private String thumbnailImage;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    /**
     * id와 oneToMany 3개 빠졌습니다.
     */
    public Room(String name, String category, Integer capacity, LocalTime weekdayRentTime,
                LocalTime weekdayRentStartTime, LocalTime weekdayRentEndTime, LocalTime weekdayCheckInTime,
                LocalTime weekdayCheckOutTime, Integer weekdayRentPrice, Integer weekdayStayPrice,
                LocalTime weekendRentTime, LocalTime weekendRentStartTime, LocalTime weekendRentEndTime,
                LocalTime weekendCheckInTime, LocalTime weekendCheckOutTime, Integer weekendRentPrice,
                Integer weekendStayPrice, Integer totalRoomCount, String thumbnailImage, Place place) {
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.weekdayRentTime = weekdayRentTime;
        this.weekdayRentStartTime = weekdayRentStartTime;
        this.weekdayRentEndTime = weekdayRentEndTime;
        this.weekdayCheckInTime = weekdayCheckInTime;
        this.weekdayCheckOutTime = weekdayCheckOutTime;
        this.weekdayRentPrice = weekdayRentPrice;
        this.weekdayStayPrice = weekdayStayPrice;
        this.weekendRentTime = weekendRentTime;
        this.weekendRentStartTime = weekendRentStartTime;
        this.weekendRentEndTime = weekendRentEndTime;
        this.weekendCheckInTime = weekendCheckInTime;
        this.weekendCheckOutTime = weekendCheckOutTime;
        this.weekendRentPrice = weekendRentPrice;
        this.weekendStayPrice = weekendStayPrice;
        this.totalRoomCount = totalRoomCount;
        this.place = place;
        this.thumbnailImage = thumbnailImage;
    }

    public Room(Long id) {
        this.id = id;
    }

    /**
     * 비즈니스 로직
     */
    public LocalTime choiceCheckInTime(LocalDate localDate) {
        if (isWeekend(localDate)) {
            return weekendCheckInTime;
        } else {
            return weekdayCheckInTime;
        }
    }

    public LocalTime choiceCheckOutTime(LocalDate localDate) {
        if (isWeekend(localDate)) {
            return weekendCheckOutTime;
        } else {
            return weekdayCheckOutTime;
        }
    }

    public Integer choiceRentPrice(LocalDate localDate) {
        if (isWeekend(localDate)) {
            return weekendRentPrice;
        } else {
            return weekdayRentPrice;
        }
    }

    public Integer choiceStayPrice(LocalDate localDate) {
        if (isWeekend(localDate)) {
            return weekendStayPrice;
        } else {
            return weekdayStayPrice;
        }
    }

    public BigDecimal calcTotalPrice(LocalDate checkInDate, LocalDate checkOutDate) {
        Long days = (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
        Integer weekDayCount = getWeekDayCount(checkInDate, checkOutDate);
        Integer weekendCount = days.intValue() - weekDayCount;

        log.info("weekDayCount = {}", weekDayCount);
        log.info("weekendCount = {}", weekendCount);

        Integer totalPrice = weekDayCount * weekdayStayPrice + weekendCount * weekendStayPrice;
        log.info("totalPrice = {}", totalPrice);

        return new BigDecimal(totalPrice);
    }
}