package com.battlecruisers.yanullja.place.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceListQueryDto {

    private Integer placeCount;

    private List<PlaceQueryDto> placeList;


}
