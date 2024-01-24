package com.battlecruisers.yanullja.place.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.region.domain.SubRegion;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.domain.Theme;
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

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_region_id")
    private SubRegion subRegion;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<Room> roomList = new ArrayList<>();

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<Theme> themeList = new ArrayList<>();

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<PlaceImage> placeImages = new ArrayList<>();

    protected Place(String name, String category, String event, String policy, String address,
        SubRegion subRegion, List<Room> roomList, List<Theme> themeList,
        List<PlaceImage> placeImages) {
        this.name = name;
        this.category = category;
        this.event = event;
        this.policy = policy;
        this.address = address;
        this.subRegion = subRegion;
        this.roomList = roomList;
        this.themeList = themeList;
        this.placeImages = placeImages;
    }

    public Place(Long id) {
        this.id = id;
    }

    public static Place createPlace(String name, String category, String event, String policy,
        String address,
        SubRegion subRegion, List<Room> roomList, List<Theme> themeList,
        List<PlaceImage> placeImages) {
        return new Place(name, category, event, policy, address, subRegion, roomList, themeList,
            placeImages);
    }
}
