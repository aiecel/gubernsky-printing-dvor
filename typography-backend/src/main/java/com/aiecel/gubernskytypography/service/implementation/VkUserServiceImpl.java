package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.model.VkUser;
import com.aiecel.gubernskytypography.repository.VkUserRepository;
import com.aiecel.gubernskytypography.service.VkUserService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VkUserServiceImpl implements VkUserService {
    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final VkUserRepository vkUserRepository;

    public VkUserServiceImpl(VkApiClient vkApiClient, GroupActor groupActor, VkUserRepository vkUserRepository) {
        this.vkApiClient = vkApiClient;
        this.groupActor = groupActor;
        this.vkUserRepository = vkUserRepository;
    }

    @Override
    public VkUser getUser(int vkId) {
        //todo change name
        return vkUserRepository.findByVkId(vkId).orElseGet(() -> register(vkId));
    }

    @Override
    public boolean isUserExists(int vkId) {
        return vkUserRepository.existsByVkId(vkId);
    }

    @Override
    public VkUser register(int vkId) {
        try {
            List<UserXtrCounters> users =  vkApiClient
                    .users()
                    .get(groupActor)
                    .userIds(String.valueOf(vkId))
                    .fields(Fields.SCREEN_NAME)
                    .execute();

            String firstName = users.get(0).getFirstName();
            String lastName = users.get(0).getLastName();

            return register(new VkUser(vkId, firstName, lastName));
        } catch (ApiException | ClientException e) {
            log.error("Can't register VK user with id {}: {}", vkId, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public VkUser register(VkUser user) {
        VkUser vkUser = vkUserRepository.save(user);
        log.info("Registered new VK user: {}", user);
        return vkUser;
    }
}
