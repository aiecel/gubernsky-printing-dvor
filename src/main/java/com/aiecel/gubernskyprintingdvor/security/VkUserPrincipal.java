package com.aiecel.gubernskyprintingdvor.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@Getter
@AllArgsConstructor
public class VkUserPrincipal implements OAuth2User, Principal {
    private final int vkId;
    private final String firstName;
    private final String lastName;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    @SuppressWarnings("ConstantConditions")
    public static VkUserPrincipal fromOAuth2User(OAuth2User oAuth2User) {
        return new VkUserPrincipal(
                oAuth2User.getAttribute("id"),
                oAuth2User.getAttribute("first_name"),
                oAuth2User.getAttribute("last_name"),
                oAuth2User.getAttributes(),
                oAuth2User.getAuthorities()
        );
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}
