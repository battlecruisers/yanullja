package com.battlecruisers.yanullja.place;

import com.battlecruisers.yanullja.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>, CustomPlaceRepository {

}
