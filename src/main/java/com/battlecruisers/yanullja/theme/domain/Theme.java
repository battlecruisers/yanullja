package com.battlecruisers.yanullja.theme.domain;

import com.battlecruisers.yanullja.place.domain.Place;
import com.battlecruisers.yanullja.theme.ThemeType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
public class Theme {

    @Id @GeneratedValue
    private Long id;

    private ThemeType type;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

}
