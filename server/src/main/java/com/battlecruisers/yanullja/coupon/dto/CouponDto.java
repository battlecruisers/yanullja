package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CouponDto {

    @Schema(name = "Coupon ID", example = "1")
    public Long id;

    @Schema(name = "Room ID", example = "3")
    public Long roomId;

    @Schema(name = "Coupon Name", example = "50% discounted coupon")
    public String name;

    @Schema(name = "Minimum Order Amount", example = "20000")
    public BigDecimal minimumPrice;

    @Schema(name = "Discounted Price", example = "10000")
    public BigDecimal discountPrice;

    @Schema(name = "Discount Rate", example = "0.5")
    public Double discountRate;

    @Schema(name = "Discount Limit", example = "5000")
    public BigDecimal discountLimit;

    @Schema(name = "Description", example = "This is a 50% discount coupon available for orders over 20000 won.")
    public String description;

    @Schema(name = "Region", example = "Seoul")
    public String region;

    @Schema(name = "Accommodation Type", example = "HOTEL")
    public RoomType roomType;

    @Schema(name = "Coupon Validity", example = "true")
    public Boolean isValid;

    @Schema(name = "Coupon Registration", example = "true")
    public Boolean isRegistered;

    @Schema(name = "Coupon Validity Start Date", example = "2024-02-21")
    public LocalDate validityStartDate;

    @Schema(name = "Coupon Validity End Date", example = "2024-12-31")
    public LocalDate validityEndDate;


    public static CouponDto from(Coupon coupon) {
        CouponDto couponDto = new CouponDto();
        couponDto.setId(coupon.getId());
        couponDto.setRoomId(coupon.getRoom().getId());
        couponDto.setName(coupon.getName());
        couponDto.setMinimumPrice(coupon.getMinimumPrice());
        couponDto.setDiscountPrice(coupon.getDiscountPrice());
        couponDto.setDiscountRate(coupon.getDiscountRate());
        couponDto.setDiscountLimit(coupon.getDiscountLimit());
        couponDto.setDescription(coupon.getDescription());
        couponDto.setRegion(coupon.getRegion());
        couponDto.setRoomType(coupon.getRoomType());
        couponDto.setIsValid(coupon.getIsValid());
        couponDto.setIsRegistered(coupon.getIsRegistered());
        couponDto.setValidityStartDate(coupon.getValidityStartDate());
        couponDto.setValidityEndDate(coupon.getValidityEndDate());
        return couponDto;
    }

}
