package com.battlecruisers.yanullja.region;

import com.battlecruisers.yanullja.region.dto.RegionListQueryDto;
import com.battlecruisers.yanullja.region.dto.RegionQueryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "메인 지역 전체 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적인 조회")
    })
    @GetMapping("/main-regions")
    public ResponseEntity<List<RegionQueryDto>> queryMainRegions() {
        List<RegionQueryDto> regionDtoList = regionService.queryMainRegions();
        return new ResponseEntity<List<RegionQueryDto>>(regionDtoList, HttpStatus.OK);
    }

//    @Operation(summary = "특정 메인 지역의 서브 지역 전체 조회")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "성공적인 조회")
//    })
//    @GetMapping("/main-regions/{mainRegionId}/sub-regions")
//    public ResponseEntity<List<RegionQueryDto>> queryMainRegions(
//        @PathVariable("mainRegionId") Long mainRegionId) {
//        List<RegionQueryDto> regionDtoList = regionService.querySubRegions(mainRegionId);
//        return new ResponseEntity<List<RegionQueryDto>>(regionDtoList, HttpStatus.OK);
//    }

    @Operation(summary = "특정 메인 지역의 서브 지역 전체 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적인 조회")
    })
    @GetMapping("/regions")
    public ResponseEntity<RegionListQueryDto> queryRegions() {
        RegionListQueryDto regionListQueryDto = regionService.queryRegions();
        return new ResponseEntity<RegionListQueryDto>(regionListQueryDto, HttpStatus.OK);
    }


}
