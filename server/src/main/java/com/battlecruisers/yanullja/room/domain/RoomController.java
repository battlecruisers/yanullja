package com.battlecruisers.yanullja.room.domain;


import com.battlecruisers.yanullja.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;


    @GetMapping("/{roomId}")
    public ResponseEntity<Object> roomDetail(){

        return ResponseEntity
                .ok()
                .build();
    }

}
