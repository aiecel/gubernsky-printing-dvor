package com.aiecel.gubernskytypography.security;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public interface UserInfoLoader {
    String clientRegistrationId();
    CustomOAuth2User loadUserInfo(OAuth2UserRequest userRequest);
}
