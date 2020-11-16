package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.VkUser;
import com.aiecel.gubernskyprintingdvor.repository.VkUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VkUserServiceImpl implements VkUserService {
    private final VkUserRepository vkUserRepository;

    @Autowired
    public VkUserServiceImpl(VkUserRepository vkUserRepository) {
        this.vkUserRepository = vkUserRepository;
    }

    @Override
    public boolean isVkUserExists(int vkId) {
        return vkUserRepository.existsByVkId(vkId);
    }

    @Override
    public void registerVkUser(VkUser user) {
        vkUserRepository.save(user);
    }
}
