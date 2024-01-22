package com.battlecruisers.yanullja.place.dto;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.place.domain.PlaceImage;
import com.battlecruisers.yanullja.room.dto.RoomListQueryDto;
import com.battlecruisers.yanullja.theme.ThemeType;
import com.battlecruisers.yanullja.theme.domain.Theme;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PlaceDetailQueryDto {

    @Schema(name = "숙소 이름", example = "신라 호텔")
    private String name;

    //TODO : 찜 여부

    @Schema(name = "시설 리스트", example = "[와이파이, 비즈니스, 어메니티]")
    private List<ThemeType> themeList = new ArrayList<>();

    @Schema(name = "시설 대표 사진 리스트", example = "[ur1, ur2, ur3]", description = "시설 대표 사진의 url을 리스트로 리턴")
    private List<String> placeImageList = new ArrayList<>();


    private List<RoomListQueryDto> roomList = new ArrayList<>();

    public PlaceDetailQueryDto(Place place, List<RoomListQueryDto> roomList) {
        this.name = place.getName();
        this.themeList = place.getThemeList().stream()
            .map(Theme::getType)
            .collect(Collectors.toList());
        this.placeImageList = place.getPlaceImages().stream()
            .map(PlaceImage::getImageUrl)
            .collect(Collectors.toList());
        this.roomList = roomList;
    }

}
