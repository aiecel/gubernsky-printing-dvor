package com.aiecel.gubernskytypography.bot.repository;

import com.aiecel.gubernskytypography.bot.model.OffSiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<OffSiteUser, Long> {
    Optional<OffSiteUser> findByUsernameAndProvider(String username, String provider);
}
