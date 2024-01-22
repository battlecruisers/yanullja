package com.battlecruisers.yanullja.room.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.place.domain.Place;
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
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private Integer weekendStayPrice;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RoomImage> roomImages = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Coupon> couponList = new ArrayList<>();
}
