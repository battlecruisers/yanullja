package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.place.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ReservationResponsePlaceDto {

    private Long accommodationId; // placeId

    private String name; // placeName

    @Schema(example = "서울시 관악구 남현동")
    private String address; // palce Address

    @Schema(example = "https://s3.us-west-1.amazonaws.com/myBucket/object%20key?query=%5Bbrackets%5D")
    private String thumbnailImageUrl;

    private List<ReservationResponseRoomDto> roomOptions; // rooms

    protected ReservationResponsePlaceDto(Place place) {
        this.accommodationId = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.thumbnailImageUrl = place.getThumbnailImageUrl();
        this.roomOptions = new ArrayList<>();
    }

    public static ReservationResponsePlaceDto createPurchasePlaceResponseDto(
        Place place) {
        return new ReservationResponsePlaceDto(place);
    }

}
