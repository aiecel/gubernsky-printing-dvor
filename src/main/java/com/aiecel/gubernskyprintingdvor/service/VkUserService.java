package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.VkUser;

public interface VkUserService {
    VkUser getUser(int vkId);
    boolean isUserExists(int vkId);
    VkUser register(int vkId);
    VkUser register(VkUser user);
}
