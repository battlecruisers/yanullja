package com.battlecruisers.yanullja.mainregion.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.subregion.domain.SubRegion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MainRegion extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

  @OneToMany(mappedBy = "mainRegion")
  private List<SubRegion> subRegionList = new ArrayList<>();
}
