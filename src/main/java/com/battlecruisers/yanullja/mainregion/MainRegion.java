package com.battlecruisers.yanullja.mainregion;

import com.battlecruisers.yanullja.subregion.SubRegion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MainRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

  @OneToMany(mappedBy = "mainRegion")
  private List<SubRegion> subRegionList = new ArrayList<>();
}
