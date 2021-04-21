package com.aiecel.gubernskytypography.security;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class VkOAuth2UserLoader implements UserInfoLoader {
    public static final String VK_CLIENT_REGISTRATION_ID = "vk";

    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;

    @Override
    public String clientRegistrationId() {
        return VK_CLIENT_REGISTRATION_ID;
    }

    public CustomOAuth2User loadUserInfo(OAuth2UserRequest oAuth2UserRequest) {
        String userNameAttributeName = oAuth2UserRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        String vkUserId = (String) oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName);

        try {
            UserXtrCounters userXtrCounters = vkApiClient
                    .users()
                    .get(groupActor)
                    .userIds(vkUserId)
                    .execute()
                    .get(0);

            String displayName = userXtrCounters.getFirstName() + " " + userXtrCounters.getLastName();

            Map<String, Object> userAttributes = new HashMap<>();
            userAttributes.put("username", vkUserId);
            userAttributes.put("displayName", displayName);
            userAttributes.put("clientRegistrationId", clientRegistrationId());

            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));

            return new CustomOAuth2User(vkUserId, displayName, clientRegistrationId(), authorities);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage() + HttpStatus.UNAUTHORIZED);
        }
    }
}
