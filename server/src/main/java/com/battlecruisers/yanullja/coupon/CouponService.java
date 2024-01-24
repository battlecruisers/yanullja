package com.battlecruisers.yanullja.coupon;

import com.battlecruisers.yanullja.coupon.domain.Coupon;
import com.battlecruisers.yanullja.coupon.domain.RoomType;
import com.battlecruisers.yanullja.coupon.dto.CouponResponseDto;
import com.battlecruisers.yanullja.coupon.exception.InvalidCouponException;
import com.battlecruisers.yanullja.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.battlecruisers.yanullja.coupon.dto.CouponResponseDto.createCouponResponseDto;

@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final RoomRepository roomRepository;


    // 적용 가능한 전체 쿠폰 조회
    public List<CouponResponseDto> getCouponList() {
        List<Coupon> couponList = couponRepository.findAll();

        // 쿠폰목록들을 DTO에 담아 반환한다.
        List<CouponResponseDto> responseDtos = new ArrayList<>();

        for (Coupon c : couponList) {
            responseDtos.add(createCouponResponseDto(c));
        }

        return responseDtos;

    }

    // 하나의 쿠폰 정보 조회
    public CouponResponseDto getCoupon(Long id) {
        Coupon coupon = couponRepository
                .findById(id)
                .orElseThrow(() -> new InvalidCouponException(id));

        CouponResponseDto couponResponseDto = createCouponResponseDto(coupon);


        return couponResponseDto;

    }

    // 쿠폰 생성

    public Long createCoupon() {
        Coupon newCoupon = Coupon.createCoupon("할인 쿠폰", BigDecimal.valueOf(40000),
                BigDecimal.valueOf(5000), 10.0, 10.0, "좋은 쿠폰", "서울", RoomType.DayUse,
                roomRepository.findById(2L).orElseThrow(), true, false);

        couponRepository.save(newCoupon);

        return newCoupon.getId();
    }


}
