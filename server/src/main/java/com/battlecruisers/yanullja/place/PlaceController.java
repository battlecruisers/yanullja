package com.battlecruisers.yanullja.place;


import com.battlecruisers.yanullja.place.dto.PlaceInfoQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.place.dto.SearchResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;


    @Operation(summary = "검색 조건에 맞는 숙소 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적인 조회")
    })
    @GetMapping("/accommodations")
    public ResponseEntity<SearchResponseDto> searchPlaces(
        SearchConditionDto searchConditionDto) {

        SearchResponseDto placeQueryDtoList = placeService.searchPlaces(searchConditionDto);
        return new ResponseEntity<SearchResponseDto>(placeQueryDtoList, HttpStatus.OK);
    }

    @Operation(summary = "특정 숙소의 상세정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적인 조회"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 숙소의 id 입력")
    })
    @GetMapping("/accommodations/{placeId}")
    public ResponseEntity<PlaceInfoQueryDto> queryPlace(@PathVariable("placeId") Long placeId,
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
        @RequestParam("guest") Integer guestCount) {
        PlaceInfoQueryDto placeRoomInfoQueryDto
            = placeService.queryPlace(placeId, checkInDate, checkOutDate, guestCount);
        return new ResponseEntity<PlaceInfoQueryDto>(placeRoomInfoQueryDto, HttpStatus.OK);
    }

}

