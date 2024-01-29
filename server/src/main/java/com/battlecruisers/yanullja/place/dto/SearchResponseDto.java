package com.battlecruisers.yanullja.place.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResponseDto {

    private List<PlaceQueryDto> content;

}
