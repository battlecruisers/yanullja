package com.battlecruisers.yanullja.reservation.dto;

import com.battlecruisers.yanullja.place.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ReservationResultPlaceDto {

    private Long accommodationId; // placeId

    private String name; // placeName

    @Schema(example = "서울시 관악구 남현동")
    private String address;

    @Schema(example = "https://s3.us-west-1.amazonaws.com/myBucket/object%20key?query=%5Bbrackets%5D")
    private String thumbnailImageUrl; // place thumbnailImageUrl

    private List<ReservationResultRoomDto> roomOptions; // rooms

    protected ReservationResultPlaceDto(Place place) {
        this.accommodationId = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.thumbnailImageUrl = place.getThumbnailImageUrl();
        this.roomOptions = new ArrayList<>();
    }

    public static ReservationResultPlaceDto createReservationResultPlaceDto(
        Place place) {
        return new ReservationResultPlaceDto(place);
    }

}
