package com.battlecruisers.yanullja.subregion.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.mainregion.domain.MainRegion;
import com.battlecruisers.yanullja.place.domain.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubRegion extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private MainRegion mainRegion;

  @OneToMany(mappedBy = "subRegion")
  private List<Place> placeList = new ArrayList<>();
}
