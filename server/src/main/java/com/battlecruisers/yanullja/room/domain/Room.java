package com.battlecruisers.yanullja.room.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.roominfo.domain.RoomInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

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

    @ManyToOne private Place place;

    @OneToMany(mappedBy = "room")
    private List<RoomInfo> roomInfoList = new ArrayList<>();
}
