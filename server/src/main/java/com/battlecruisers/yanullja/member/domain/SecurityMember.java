package com.battlecruisers.yanullja.member.domain;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class SecurityMember extends User implements Serializable {

    private Long id;
    private String provider;
    private String providerId;

    public SecurityMember(Member member) {
        super(member.getProviderId(), member.getProvider(), List.of());
        this.id = member.getId();
        this.provider = member.getProvider();
        this.providerId = member.getProviderId();
    }

}
