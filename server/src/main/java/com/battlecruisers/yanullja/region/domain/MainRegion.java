package com.battlecruisers.yanullja.region.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MainRegion extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "mainRegion")
    private final List<SubRegion> subRegionList = new ArrayList<>();

    public MainRegion(String name) {
        this.name = name;
    }
}
