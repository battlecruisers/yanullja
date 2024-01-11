package com.battlecruisers.yanullja.room;

import com.battlecruisers.yanullja.place.Place;
import com.battlecruisers.yanullja.subregion.SubRegion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    @ManyToOne
    private Place place;
}
