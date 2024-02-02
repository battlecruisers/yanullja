package com.battlecruisers.yanullja.room;


import com.battlecruisers.yanullja.room.domain.Room;
import com.battlecruisers.yanullja.room.dto.RoomDto;
import com.battlecruisers.yanullja.room.dto.RoomReservationInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

//    public fromEntityToDto(Room room, RoomDto roomDto) {
//        this.roomMapper.convert(room, roomDto);
//    }

    public RoomDto getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(IllegalArgumentException::new);

//        return RoomDto.createNewRoomDto(room);
        return null;
    }

    @Transactional(readOnly = true)
    public RoomReservationInfoDto queryRoomDetailForReservation(Long roomId) {
        Room room = roomRepository.queryRoomById(roomId)
                .orElseThrow(IllegalArgumentException::new);

        return RoomReservationInfoDto.createRoomReservationInfoDto(room);
    }
}
