package com.aiecel.gubernskyprintingdvor.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        if (userRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase("vk")) {
            return VkOAuth2UserLoader.loadUser(userRequest);
        } else {
            return super.loadUser(userRequest);
        }
    }
}
