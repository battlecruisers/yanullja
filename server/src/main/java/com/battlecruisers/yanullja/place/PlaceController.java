package com.battlecruisers.yanullja.place;


import com.battlecruisers.yanullja.place.dto.PlaceInfoQueryDto;
import com.battlecruisers.yanullja.place.dto.SearchConditionDto;
import com.battlecruisers.yanullja.place.dto.SearchResponseDto;
import com.battlecruisers.yanullja.review.ReviewService;
import com.battlecruisers.yanullja.review.dto.ReviewDetailDto;
import com.battlecruisers.yanullja.review.dto.ReviewSampleDto;
import com.battlecruisers.yanullja.review.dto.ReviewSearchCond;
import com.battlecruisers.yanullja.review.dto.ReviewStatisticsDto;
import com.battlecruisers.yanullja.review.exception.NoReviewsException;
import com.battlecruisers.yanullja.room.RoomService;
import com.battlecruisers.yanullja.room.dto.RoomNameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final ReviewService reviewService;

    private final RoomService roomService;

    @ExceptionHandler
    public ResponseEntity<Object> noReviewsExceptionHandler(NoReviewsException e) {
        return ResponseEntity.ok().build();
    }

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

    @Operation(summary = "특정 지역의 모든 숙소 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 조회")
    })
    @GetMapping("/accommodations/region")
    public ResponseEntity<SearchResponseDto> queryPlacesInRegion(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("guest") Integer guestCount,
            @RequestParam("region") String regionName
    ) {
        SearchResponseDto searchResponseDto = placeService.queryPlacesInRegion(checkInDate,
                checkOutDate, guestCount, regionName);
        return new ResponseEntity<SearchResponseDto>(searchResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "특정 카테고리의 모든 숙소 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 조회")
    })
    @GetMapping("/accommodations/category")
    public ResponseEntity<SearchResponseDto> queryPlacesInCategory(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("guest") Integer guestCount,
            @RequestParam("category") String categoryName
    ) {
        SearchResponseDto searchResponseDto = placeService.queryPlaceInCategory(checkInDate,
                checkOutDate, guestCount, categoryName);
        return new ResponseEntity<SearchResponseDto>(searchResponseDto, HttpStatus.OK);
    }


    @GetMapping("/accommodations/{placeId}/room-names")
    public ResponseEntity<List<RoomNameDto>> fetchAllRooms(@PathVariable Long placeId) {

        List<RoomNameDto> reviews = roomService.getAllRoomsName(placeId);

        return ResponseEntity
                .ok()
                .body(reviews);
    }

    @GetMapping("/accommodations/{placeId}/reviews")
    public ResponseEntity<Slice<ReviewDetailDto>> fetchReviews(@PathVariable Long placeId,
                                                               @RequestParam(value = "roomId", required = false) Long roomId,
                                                               @RequestParam(value = "photo", required = false, defaultValue = "false") boolean photo,
                                                               @PageableDefault(size = 15, sort = "createdDate") Pageable pageable) {

        ReviewSearchCond cond = new ReviewSearchCond(placeId, roomId, photo, pageable);
        Slice<ReviewDetailDto> reviews = reviewService.getReviewDetails(cond, pageable);

        return ResponseEntity
                .ok()
                .body(reviews);
    }

    @GetMapping("/accommodations/{placeId}/review-samples")
    public ResponseEntity<ReviewSampleDto> fetchReviewSamples(@PathVariable(value = "placeId") Long placeId,
                                                              @RequestParam(value = "roomId", required = false) Long roomId) {

        ReviewSampleDto reviewSamples = reviewService.getReviewSamples(placeId, roomId);

        return ResponseEntity
                .ok()
                .body(reviewSamples);
    }


    @GetMapping("/accommodations/{placeId}/review-statistics")
    public ResponseEntity<ReviewStatisticsDto> fetchReviewStatistics(@PathVariable(value = "placeId") Long placeId,
                                                                     @RequestParam(value = "roomId", required = false) Long roomId) {

        ReviewStatisticsDto reviewStatistics = reviewService.getReviewStatistics(placeId, roomId);

        return ResponseEntity
                .ok()
                .body(reviewStatistics);
    }

}

