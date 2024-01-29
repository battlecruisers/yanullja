package com.battlecruisers.yanullja.place.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.place.PlaceCategory;
import com.battlecruisers.yanullja.region.domain.SubRegion;
import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.theme.domain.Theme;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.battlecruisers.yanullja.place.PlaceService.getWeekDayCount;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseDate {

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private final List<Room> roomList = new ArrayList<>();
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private final List<Theme> themeList = new ArrayList<>();
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private final List<PlaceImage> placeImages = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private PlaceCategory category;

    private String thumbnailImageUrl;


    private String event;
    private String description;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_region_id")
    private SubRegion subRegion;

    protected Place(String name, PlaceCategory category, String thumbnailImageUrl, String event,
                    String description, String address, SubRegion subRegion) {
        this.name = name;
        this.category = category;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.event = event;
        this.description = description;
        this.address = address;
        this.subRegion = subRegion;
    }

    public Place(Long id) {
        this.id = id;
    }

    public static Place createPlace(String name, PlaceCategory category, String thumbnailImageUrl,
                                    String event, String policy, String address, SubRegion subRegion) {
        return new Place(name, category, thumbnailImageUrl, event, policy, address, subRegion);
    }

    /**
     * 비즈니스 로직
     */
    public Integer getMinimumPrice(LocalDate checkInDate, LocalDate checkOutDate) {
        Integer weekDayCount = getWeekDayCount(checkInDate, checkOutDate);
        Integer weekendCount =
                (int) (checkOutDate.toEpochDay() - checkInDate.toEpochDay()) - weekDayCount;

        return roomList.stream()
                .mapToInt(room -> calculateMinimumPrice(room, weekDayCount, weekendCount))
                .min().orElse(0);
    }

    public Integer getMaxPrice(LocalDate checkInDate, LocalDate checkOutDate) {
        Integer weekDayCount = getWeekDayCount(checkInDate, checkOutDate);
        Integer weekendCount =
                (int) (checkOutDate.toEpochDay() - checkInDate.toEpochDay()) - weekDayCount;

        return roomList.stream()
                .mapToInt(room -> calculateMaxPrice(room, weekDayCount, weekendCount))
                .max().orElse(0);
    }

    private Integer calculateMinimumPrice(Room room, Integer weekDayCount, Integer weekendCount) {
        if (weekendCount > 0 && weekDayCount > 0) {
            return Math.min(Math.min(room.getWeekdayRentPrice(), room.getWeekendRentPrice()),
                    Math.min(room.getWeekdayStayPrice(), room.getWeekendStayPrice()));
        } else if (weekendCount == 0) {
            return Math.min(room.getWeekdayRentPrice(), room.getWeekdayStayPrice());
        } else {
            return Math.min(room.getWeekendStayPrice(), room.getWeekendRentPrice());
        }
    }


    private Integer calculateMaxPrice(Room room, Integer weekDayCount, Integer weekendCount) {
        if (weekendCount > 0 && weekDayCount > 0) {
            return Math.max(Math.max(room.getWeekdayRentPrice(), room.getWeekendRentPrice()),
                    Math.max(room.getWeekdayStayPrice(), room.getWeekendStayPrice()));
        } else if (weekendCount == 0) {
            return Math.max(room.getWeekdayRentPrice(), room.getWeekdayStayPrice());
        } else {
            return Math.max(room.getWeekendStayPrice(), room.getWeekendRentPrice());
        }
    }
}
