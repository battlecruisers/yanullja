package com.battlecruisers.yanullja.room;


import com.battlecruisers.yanullja.room.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;


    @GetMapping("/{roomId}")
    public ResponseEntity<Object> roomDetail(@PathVariable Long roomId) {

        RoomDto room = roomService.getRoom(roomId);

        return ResponseEntity
            .ok()
            .body(room);
    }

}
