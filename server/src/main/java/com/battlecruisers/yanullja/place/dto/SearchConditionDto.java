package com.battlecruisers.yanullja.place.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class SearchConditionDto {

    @Schema(name = "체크인 날짜", requiredMode = REQUIRED, example = "2024-01-20", description = "YYYY-MM-DD 형식으로 전송")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Schema(name = "체크아웃 날짜", requiredMode = REQUIRED, example = "2024-01-25", description = "YYYY-MM-DD 형식으로 전송")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Schema(name = "투숙 인원", requiredMode = NOT_REQUIRED, example = "4")
    private Integer guest;

    @Schema(name = "대실 가능 여부", requiredMode = NOT_REQUIRED, example = "1", description = "대실 가능하면 1, 대실 불가능하면 0")
    private Integer rentable;

    @Schema(name = "숙박 가능 여부", requiredMode = NOT_REQUIRED, example = "1", description = "숙박 가능하면 1, 숙박 불가능하면 0")
    private Integer stayable;

    @Schema(name = "쿠폰 사용 여부", requiredMode = NOT_REQUIRED, example = "true")
    private Boolean applicable;

    @Schema(name = "판매완료 여부", requiredMode = NOT_REQUIRED, example = "1", description = "판매 완료되어 해당 숙소에 이용 가능한 방이 없는 경우 0, 해당 숙소에 이용 가능한 방이 있는 경우 1")
    private Integer isSoldOut;

    @Schema(name = "최소 금액", requiredMode = NOT_REQUIRED, example = "10000", defaultValue = "0")
    private Integer minPrice;

    @Schema(name = "최대 금액", requiredMode = NOT_REQUIRED, example = "10000", defaultValue = "0")
    private Integer maxPrice;

    @Schema(name = "테마 리스트", requiredMode = NOT_REQUIRED, example = "수영장,애견동반,주방", description = "각 테마의 이름을 ','를 이용해서 연결해서 하나의 문자열로 보내기")
    private String theme;

    @Schema(name = "정렬", requiredMode = NOT_REQUIRED, example = "REVIEW_GOOD", description = "REVIEW_GOOD, REVIEW_MANY, BOOKMARK_MANY, PRICE_LOW, PRICE_HIGH")
    private String sort;

    private String name;
}
