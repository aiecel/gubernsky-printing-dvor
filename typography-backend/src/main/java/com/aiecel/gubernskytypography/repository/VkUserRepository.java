package com.aiecel.gubernskytypography.repository;

import com.aiecel.gubernskytypography.model.VkUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VkUserRepository extends JpaRepository<VkUser, Long> {
    Optional<VkUser> findByVkId(int vkId);
    boolean existsByVkId(int vkId);
}
