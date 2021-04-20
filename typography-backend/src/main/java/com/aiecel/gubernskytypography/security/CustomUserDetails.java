package com.aiecel.gubernskytypography.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

public class CustomUserDetails extends AuthenticatedUser implements UserDetails {
    public CustomUserDetails(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
        super(attributes, authorities);
    }

    @Override
    public String getPassword() {
        return getAttributes().get("password").toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
