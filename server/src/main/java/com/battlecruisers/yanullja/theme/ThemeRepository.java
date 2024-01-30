package com.battlecruisers.yanullja.theme;

import com.battlecruisers.yanullja.theme.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findAllByTypeIn(List<ThemeType> themeTypeList);
}
