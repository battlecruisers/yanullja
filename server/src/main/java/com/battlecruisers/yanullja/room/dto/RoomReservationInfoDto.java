package com.battlecruisers.yanullja.room.dto;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomReservationInfoDto {

    private Long placeId;
    private String placeName;
    private String address;
    private String placeThumbnailImageUrl;

    private Long roomId;
    private String roomName;
    private Integer capacity;

    public static RoomReservationInfoDto createRoomReservationInfoDto(Room room) {
        Place place = room.getPlace();
        return new RoomReservationInfoDto(place.getId(), place.getName(), place.getAddress(), place.getThumbnailImageUrl(),
                room.getId(), room.getName(), room.getCapacity());
    }
}
