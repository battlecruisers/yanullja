package com.battlecruisers.yanullja.region.dto;

import com.battlecruisers.yanullja.region.domain.MainRegion;
import com.battlecruisers.yanullja.region.domain.SubRegion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegionQueryDto {

    @Schema(name = "지역ID", example = "1")
    private Long id;

    @Schema(name = "지역 이름", example = "강릉")
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
