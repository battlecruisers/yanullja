package com.battlecruisers.yanullja.review.domain;


import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
public class Review extends BaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Double kindnessRate;

    private Double cleanlinessRate;

    private Double convenienceRate;

    private Double locationRate;

    private Double totalRate;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImages;

}
