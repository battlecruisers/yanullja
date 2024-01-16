package com.battlecruisers.yanullja.region;

import com.battlecruisers.yanullja.region.domain.SubRegion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {

    List<SubRegion> findAllByMainRegionId(Long mainRegionId);
}
