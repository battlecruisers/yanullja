package com.battlecruisers.yanullja.common.security;

import com.battlecruisers.yanullja.member.MemberRepository;
import com.battlecruisers.yanullja.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByProviderId(username)
                .orElseThrow(() -> new RuntimeException("Provider Id not found!"));
        return User.builder()
                .username(member.getProviderId())
                .password(member.getProvider())
                .build();
    }
}
