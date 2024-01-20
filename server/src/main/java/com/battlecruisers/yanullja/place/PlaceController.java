package com.battlecruisers.yanullja.place;


import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
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
    @GetMapping("/search/{keyword}")
    public ResponseEntity<Void> searchPlaces(@PathVariable("keyword") String keyword,
        SearchConditionDto searchConditionDto) {

        placeService.searchPlaces(keyword, searchConditionDto);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
