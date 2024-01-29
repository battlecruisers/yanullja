package com.battlecruisers.yanullja.region.domain;

import com.battlecruisers.yanullja.base.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainRegion extends BaseDate {

    @OneToMany(mappedBy = "mainRegion", fetch = FetchType.LAZY)
    private final List<SubRegion> subRegionList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected MainRegion(String name) {
        this.name = name;
    }

    public MainRegion(Long id) {
        this.id = id;
    }

    public static MainRegion createMainRegion(String name) {
        return new MainRegion(name);
    }
}
