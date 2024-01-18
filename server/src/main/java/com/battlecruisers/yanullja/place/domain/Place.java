package com.battlecruisers.yanullja.place.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.region.domain.SubRegion;
import com.battlecruisers.yanullja.room.domain.Room;
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
public class Place extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String event;
    private String policy;

    @ManyToOne
    private SubRegion subRegion;

    @OneToMany(mappedBy = "place")
    private List<Room> roomList = new ArrayList<>();
}
