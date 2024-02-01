package com.battlecruisers.yanullja.review.domain;


import com.battlecruisers.yanullja.base.BaseDate;
import com.battlecruisers.yanullja.member.domain.Member;
import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.review.dto.ReviewSaveDto;
import com.battlecruisers.yanullja.room.domain.Room;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    public Review(Long id) {
        this.id = id;
    }

    private Review(String content, Double kindnessRate, Double cleanlinessRate,
        Double convenienceRate, Double locationRate, Double totalRate,
        Member member, Place place, Room room) {
        this.content = content;
        this.kindnessRate = kindnessRate;
        this.cleanlinessRate = cleanlinessRate;
        this.convenienceRate = convenienceRate;
        this.locationRate = locationRate;
        this.totalRate = totalRate;
        this.member = member;
        this.place = place;
        this.room = room;
    }

    public static Review from(ReviewSaveDto form) {
        return new Review(
            form.getContent(),
            form.getKindnessRate(),
            form.getCleanlinessRate(),
            form.getConvenienceRate(),
            form.getLocationRate(),
            form.getTotalRate(),
            new Member(form.getMemberId()),
            new Place(form.getPlaceId()),
            new Room(form.getRoomId())
        );
    }

}
