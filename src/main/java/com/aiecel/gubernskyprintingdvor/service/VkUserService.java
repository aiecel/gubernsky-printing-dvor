package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.VkUser;

public interface VkUserService {
    boolean isUserExists(int vkId);
    VkUser register(int vkId);
    VkUser register(VkUser user);
}
