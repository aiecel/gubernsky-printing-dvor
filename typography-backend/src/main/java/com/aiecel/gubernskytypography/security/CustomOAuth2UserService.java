package com.aiecel.gubernskytypography.security;

import com.aiecel.gubernskytypography.model.VkUser;
import com.aiecel.gubernskytypography.service.VkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final VkUserService vkUserService;

    @Autowired
    public CustomOAuth2UserService(VkUserService vkUserService) {
        this.vkUserService = vkUserService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        if (userRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase("vk")) {
            OAuth2User oAuth2User = VkOAuth2UserLoader.loadUser(userRequest);

            @SuppressWarnings("ConstantConditions")
            int vkId = oAuth2User.getAttribute("id");

            if (!vkUserService.isUserExists(vkId)) {
                vkUserService.register(
                        new VkUser(vkId,
                                oAuth2User.getAttribute("first_name"),
                                oAuth2User.getAttribute("last_name")
                        )
                );
            }
            return VkUserPrincipal.fromOAuth2User(oAuth2User);
        } else {
            return super.loadUser(userRequest);
        }
    }
}
