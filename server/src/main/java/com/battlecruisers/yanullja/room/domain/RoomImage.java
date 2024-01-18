package com.battlecruisers.yanullja.room.domain;

import com.battlecruisers.yanullja.review.domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
public class RoomImage {

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;

}
