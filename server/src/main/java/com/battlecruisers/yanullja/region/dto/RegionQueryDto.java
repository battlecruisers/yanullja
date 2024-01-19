package com.battlecruisers.yanullja.region.dto;

import com.battlecruisers.yanullja.region.domain.MainRegion;
import com.battlecruisers.yanullja.region.domain.SubRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegionQueryDto {

    private Long id;
    private String name;

    public RegionQueryDto(MainRegion mainRegion) {
        this.id = mainRegion.getId();
        this.name = mainRegion.getName();
    }

    public RegionQueryDto(SubRegion subRegion) {
        this.id = subRegion.getId();
        this.name = subRegion.getName();
    }
}
