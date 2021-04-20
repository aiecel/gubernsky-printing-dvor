package com.aiecel.gubernskytypography.security;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserInfoLoader {
    String clientRegistrationId();
    OAuth2User loadUserInfo(OAuth2UserRequest userRequest);
}
