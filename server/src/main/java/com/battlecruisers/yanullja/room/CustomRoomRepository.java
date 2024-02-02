package com.battlecruisers.yanullja.room;

import com.battlecruisers.yanullja.room.domain.Room;

import java.util.Optional;

public interface CustomRoomRepository {

    Optional<Room> queryRoomById(Long roomId);
}
