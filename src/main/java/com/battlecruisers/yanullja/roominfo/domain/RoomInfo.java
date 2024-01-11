package com.battlecruisers.yanullja.roominfo.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RoomInfo extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rentTime;
    private Integer rentStartTime;
    private Integer rentEndTime;
    private Integer checkInTime;
    private Integer checkOutTime;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private UsageDayType usageDayType;


}
