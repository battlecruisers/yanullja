package com.battlecruisers.yanullja.place.dto;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.room.dto.RoomQueryDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceInfoQueryDto {

    private Long id;
    private String name;
    private String category;
    private String region;
    private String address;
    private String description;
    private String thumbnailImageUrl;
    private List<RoomQueryDto> roomOptions;

    public PlaceInfoQueryDto(Place place, List<RoomQueryDto> roomOptions) {
        this.id = place.getId();
        this.name = place.getName();
        this.category = place.getCategory().getName();
        this.region = place.getSubRegion().getName();
        this.address = place.getAddress();
        this.description = place.getDescription();
        this.thumbnailImageUrl = place.getThumbnailImageUrl();
        this.roomOptions = roomOptions;
    }
}
