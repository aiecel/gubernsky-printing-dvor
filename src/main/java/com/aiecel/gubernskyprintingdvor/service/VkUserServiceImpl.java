package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.VkUser;
import com.aiecel.gubernskyprintingdvor.repository.VkUserRepository;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkUserServiceImpl implements VkUserService {
    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final VkUserRepository vkUserRepository;

    @Autowired
    public VkUserServiceImpl(VkApiClient vkApiClient, GroupActor groupActor, VkUserRepository vkUserRepository) {
        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
        this.vkUserRepository = vkUserRepository;
    }

    @Override
    public boolean isVkUserExists(int vkId) {
        return vkUserRepository.existsByVkId(vkId);
    }

    @Override
    public void registerVkUser(int vkId) {
        try {
            List<UserXtrCounters> users =  vkApiClient
                    .users()
                    .get(groupActor)
                    .userIds(String.valueOf(vkId))
                    .fields(Fields.SCREEN_NAME)
                    .execute();

            String firstName = users.get(0).getFirstName();
            String lastName = users.get(0).getLastName();

            registerVkUser(new VkUser(vkId, firstName, lastName));
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerVkUser(VkUser user) {
        vkUserRepository.save(user);
    }
}
