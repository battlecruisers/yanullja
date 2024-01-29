package com.battlecruisers.yanullja.place.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.region.domain.SubRegion;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.domain.Theme;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "place")
    private List<Theme> themeList = new ArrayList<>();
}
