package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderId(String username);
}
