package com.battlecruisers.yanullja.review.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ReviewSaveDto {

    @Schema(description = "숙소 ID")
    @NotNull(message = "숙소 ID가 없습니다.")
    private Long placeId;

    @Schema(description = "방 ID")
    @NotNull(message = "방 ID가 없습니다.")
    private Long roomId;

    @Schema(description = "회원 ID")
    @Length(min = 1, max = 1000, message = "최소 1자 최대 1000자까지 입력 가능합니다.")
    private String content;

    @Schema(description = "친절도")
    @DecimalMin(value = "0", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    @DecimalMax(value = "5", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    private Double kindnessRate;

    @Schema(description = "청결도")
    @DecimalMin(value = "0", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    @DecimalMax(value = "5", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    private Double cleanlinessRate;

    @Schema(description = "편의 수준")
    @DecimalMin(value = "0", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    @DecimalMax(value = "5", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    private Double convenienceRate;

    @Schema(description = "위치 점수")
    @DecimalMin(value = "0", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    @DecimalMax(value = "5", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    private Double locationRate;

    @Schema(description = "총 평점")
    @DecimalMin(value = "0", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    @DecimalMax(value = "5", message = "평점은 0보다 크고 5보다 작아야 합니다.")
    private Double totalRate;
}
