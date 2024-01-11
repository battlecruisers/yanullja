package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.room.Room;
import com.battlecruisers.yanullja.subregion.SubRegion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String event;
    private String policy;

    @ManyToOne
    private SubRegion subRegion;

  @OneToMany(mappedBy = "place")
  private List<Room> roomList = new ArrayList<>();
}
