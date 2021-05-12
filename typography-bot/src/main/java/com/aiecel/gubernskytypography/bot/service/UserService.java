package com.aiecel.gubernskytypography.bot.service;

import com.aiecel.gubernskytypography.bot.model.OffSiteUser;

import java.util.Optional;

public interface UserService {
    Optional<OffSiteUser> get(String username, String provider);
    OffSiteUser register(OffSiteUser user);
}
