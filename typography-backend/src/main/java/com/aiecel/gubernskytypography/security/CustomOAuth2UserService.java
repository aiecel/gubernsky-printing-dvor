package com.aiecel.gubernskytypography.security;

import com.aiecel.gubernskytypography.model.OffSiteUser;
import com.aiecel.gubernskytypography.service.OffSiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final Set<UserInfoLoader> userInfoLoaders;
    private final OffSiteUserService offSiteUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = loadUserInfo(userRequest).orElseGet(() -> {
            OAuth2User user = super.loadUser(userRequest);
            user.getAttributes().put("registration", userRequest.getClientRegistration().getRegistrationId());
            return user;
        });
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2User.getAttributes(), oAuth2User.getAuthorities());

        OffSiteUser offSiteUser = new OffSiteUser();
        offSiteUser.setUsername(customOAuth2User.getUsername());
        offSiteUser.setDisplayName(customOAuth2User.getDisplayName());
        offSiteUser.setRegistration(customOAuth2User.getRegistration());

        if (!offSiteUserService.exists(offSiteUser)) {
            offSiteUserService.register(offSiteUser);
        }

        return customOAuth2User;
    }

    private Optional<OAuth2User> loadUserInfo(OAuth2UserRequest userRequest) {
        String userRequestClientRegistrationId = userRequest.getClientRegistration().getRegistrationId();

        for (UserInfoLoader userInfoLoader : userInfoLoaders) {
            if (userInfoLoader.clientRegistrationId().equals(userRequestClientRegistrationId)) {
                return Optional.of(userInfoLoader.loadUserInfo(userRequest));
            }
        }

        return Optional.empty();
    }
}
