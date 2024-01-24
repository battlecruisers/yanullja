package com.battlecruisers.yanullja.room.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseDate {

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private final List<Reservation> reservations = new ArrayList<>();
    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<RoomImage> roomImages = new ArrayList<>();
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private final List<Coupon> coupons = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Integer capacity;
    private Integer weekdayRentTime;
    private Integer weekdayRentStartTime;
    private Integer weekdayRentEndTime;
    private Integer weekdayCheckInTime;
    private Integer weekdayCheckOutTime;
    private Integer weekdayRentPrice;
    private Integer weekdayStayPrice;
    private Integer weekendRentTime;
    private Integer weekendRentStartTime;
    private Integer weekendRentEndTime;
    private Integer weekendCheckInTime;
    private Integer weekendCheckOutTime;
    private Integer weekendRentPrice;
    private Integer weekendStayPrice;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    protected Room(String name, String category, Integer capacity, Integer weekdayRentTime,
        Integer weekdayRentStartTime, Integer weekdayRentEndTime, Integer weekdayCheckInTime,
        Integer weekdayCheckOutTime, Integer weekdayRentPrice, Integer weekdayStayPrice,
        Integer weekendRentTime, Integer weekendRentStartTime, Integer weekendRentEndTime,
        Integer weekendCheckInTime, Integer weekendCheckOutTime, Integer weekendRentPrice,
        Integer weekendStayPrice, Place place) {
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
        this.place = place;
    }

    public Room(Long id) {
        this.id = id;
    }

    public static Room createRoom(String name, String category, Integer capacity,
        Integer weekdayRentTime,
        Integer weekdayRentStartTime, Integer weekdayRentEndTime, Integer weekdayCheckInTime,
        Integer weekdayCheckOutTime, Integer weekdayRentPrice, Integer weekdayStayPrice,
        Integer weekendRentTime, Integer weekendRentStartTime, Integer weekendRentEndTime,
        Integer weekendCheckInTime, Integer weekendCheckOutTime, Integer weekendRentPrice,
        Integer weekendStayPrice, Place place) {
        return new Room(name, category, capacity, weekdayRentTime, weekdayRentStartTime,
            weekdayRentEndTime,
            weekdayCheckInTime, weekdayCheckOutTime, weekdayRentPrice, weekdayStayPrice,
            weekendRentTime, weekendRentStartTime, weekendRentEndTime, weekendCheckInTime,
            weekendCheckOutTime, weekendRentPrice, weekendStayPrice, place);
    }
}
