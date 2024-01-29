package com.battlecruisers.yanullja.region.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionListQueryDto {

    private List<String> regions;

}
