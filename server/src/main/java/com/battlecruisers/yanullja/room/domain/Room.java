package com.battlecruisers.yanullja.room.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.reservation.domain.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Room extends BaseDate {

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

    private Integer weekendSentPrice;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RoomImage> roomImages = new ArrayList<>();

    public Room() {
    }

    public Room(Long id) {
        this.id = id;
    }

    // 테스트용 생성자
    public Room(Long id, Place place, Integer capacity) {
        this.id = id;
        this.place = place;
        this.capacity = capacity;
    }

}
