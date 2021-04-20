package com.aiecel.gubernskytypography.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class AuthenticatedUser {
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public String getUsername() {
        return attributes.get("username").toString();
    }

    public String getRegistration() {
        return attributes.get("registration").toString();
    }
}
