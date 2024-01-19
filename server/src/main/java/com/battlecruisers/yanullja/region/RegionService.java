package com.battlecruisers.yanullja.region;

import com.battlecruisers.yanullja.region.dto.RegionQueryDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final MainRegionRepository mainRegionRepository;
    private final SubRegionRepository subRegionRepository;

    @Transactional(readOnly = true)
    public List<RegionQueryDto> queryMainRegions() {
        return mainRegionRepository.findAll().stream()
            .map(RegionQueryDto::new)
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<RegionQueryDto> querySubRegions(Long mainRegionId) {

        mainRegionRepository.findById(mainRegionId).orElseThrow(() -> {
            throw new NotFoundException("존재하지 않는 아이디 입니다.");
        });

        return subRegionRepository.findAllByMainRegionId(mainRegionId).stream()
            .map(RegionQueryDto::new)
            .collect(Collectors.toList());
    }
}
