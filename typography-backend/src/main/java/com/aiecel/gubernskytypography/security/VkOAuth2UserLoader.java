package com.aiecel.gubernskytypography.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class VkOAuth2UserLoader {
    @SuppressWarnings("unchecked")
    public static OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        RestTemplate template = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", oAuth2UserRequest.getAccessToken().getTokenType().getValue() + " " + oAuth2UserRequest.getAccessToken().getTokenValue());
        HttpEntity<?> httpRequest = new HttpEntity<>(headers);
        String uri = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        uri = uri.replace("{user_id}", userNameAttributeName + "=" + oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));

        try {
            ResponseEntity<Object> entity = template.exchange(uri, HttpMethod.GET, httpRequest, Object.class);
            Map<String, Object> response = (Map<String, Object>) entity.getBody();
            ArrayList<LinkedHashMap<String, Object>> valueList = (ArrayList<LinkedHashMap<String, Object>>) response.get("response");
            Map<String, Object> userAttributes = valueList.get(0);
            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            return new DefaultOAuth2User(authorities, userAttributes, "first_name");
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage() + HttpStatus.UNAUTHORIZED);
        }
    }
}
