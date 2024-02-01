package com.battlecruisers.yanullja.review.dto;


import com.querydsl.core.types.Order;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class ReviewSearchCond {

    private Long placeId;
    private Long roomId;
    private Boolean hasPhoto;
    private String orderProperty;
    private Order direction;

    public ReviewSearchCond(Long placeId, Long roomId, Boolean hasPhoto,
        Pageable pageable) {
        this.placeId = placeId;
        this.roomId = roomId;
        this.hasPhoto = hasPhoto;
        Sort.Order order = pageable.getSort().stream().findAny()
            .orElseThrow(IllegalStateException::new);
        this.orderProperty = order.getProperty();
        this.direction =
            order.getDirection() == Sort.Direction.ASC ? Order.ASC : Order.DESC;
    }
}
