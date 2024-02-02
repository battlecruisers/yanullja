package com.battlecruisers.yanullja.room;

import com.battlecruisers.yanullja.room.domain.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.battlecruisers.yanullja.place.domain.QPlace.place;
import static com.battlecruisers.yanullja.room.domain.QRoom.room;

@RequiredArgsConstructor
public class CustomRoomRepositoryImpl implements CustomRoomRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<Room> queryRoomById(Long roomId) {
        Room findRoom = jpaQueryFactory.selectFrom(room)
                .leftJoin(room.place, place).fetchJoin()
                .where(room.id.eq(roomId))
                .fetchOne();
        return Optional.ofNullable(findRoom);
    }
}
