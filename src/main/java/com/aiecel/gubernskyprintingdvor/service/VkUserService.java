package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.model.VkUser;

public interface VkUserService {
    boolean isUserExists(int vkId);
    void register(int vkId);
    void register(VkUser user);
}
