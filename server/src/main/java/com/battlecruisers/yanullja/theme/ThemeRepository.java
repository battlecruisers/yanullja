package com.battlecruisers.yanullja.theme;

import com.battlecruisers.yanullja.theme.domain.Theme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findAllByTypeIn(List<ThemeType> themeTypeList);
}
