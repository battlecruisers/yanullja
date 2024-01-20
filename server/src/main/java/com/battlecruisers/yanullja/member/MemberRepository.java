package com.battlecruisers.yanullja.member;

import com.battlecruisers.yanullja.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
