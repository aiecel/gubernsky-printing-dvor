package com.aiecel.gubernskyprintingdvor.repository;

import com.aiecel.gubernskyprintingdvor.model.VkUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VkUserRepository extends JpaRepository<VkUser, Long> {
    boolean existsByVkId(int vkId);
}
