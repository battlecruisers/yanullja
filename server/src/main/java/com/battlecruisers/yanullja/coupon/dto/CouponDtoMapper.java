package com.battlecruisers.yanullja.coupon.dto;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponDtoMapper {
    @Mapping(target = "roomId", source = "room.id")
    CouponDto toCouponDto(Coupon coupon);
}
