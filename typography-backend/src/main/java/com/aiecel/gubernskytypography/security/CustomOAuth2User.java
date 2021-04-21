package com.aiecel.gubernskytypography.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {
    private final String username;

    @Getter
    private final String displayName;

    @Getter
    private final String clientRegistrationId;

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2User(String username,
                            String displayName,
                            String clientRegistrationId,
                            Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.displayName = displayName;
        this.clientRegistrationId = clientRegistrationId;
        this.authorities = authorities;

        this.attributes = new HashMap<>();
        attributes.put("username", username);
        attributes.put("displayName", displayName);
        attributes.put("clientRegistrationId", clientRegistrationId);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
