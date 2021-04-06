package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.model.VkUser;

public interface VkUserService {
    VkUser getUser(int vkId);
    boolean isUserExists(int vkId);
    VkUser register(int vkId);
    VkUser register(VkUser user);
}
