package com.aiecel.gubernskytypography.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends AuthenticatedUser implements OAuth2User {
    public CustomOAuth2User(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
        super(attributes, authorities);
    }

    @Override
    public String getName() {
        return getUsername();
    }

    public String getDisplayName() {
        String displayNameAttribute = (String) getAttributes().get("displayName");
        if (displayNameAttribute != null) return displayNameAttribute;
        return getName();
    }
}
