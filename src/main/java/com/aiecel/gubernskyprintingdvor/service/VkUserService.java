package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.VkUser;

public interface VkUserService {
    boolean isVkUserExists(int vkId);
    void registerVkUser(int vkId);
    void registerVkUser(VkUser user);
}
