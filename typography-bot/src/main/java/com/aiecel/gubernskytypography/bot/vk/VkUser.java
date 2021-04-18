package com.aiecel.gubernskytypography.bot.vk;

import com.aiecel.gubernskytypography.bot.api.User;
import lombok.Value;

@Value
public class VkUser implements User {
    int vkId;

    @Override
    public String getId() {
        return String.valueOf(vkId);
    }
}
