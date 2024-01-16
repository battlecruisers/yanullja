package com.battlecruisers.yanullja.region.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.place.domain.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubRegion extends BaseDate {

    @OneToMany(mappedBy = "subRegion")
    private final List<Place> placeList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private MainRegion mainRegion;

    public SubRegion(String name, MainRegion mainRegion) {
        this.name = name;
        this.mainRegion = mainRegion;
    }
}
