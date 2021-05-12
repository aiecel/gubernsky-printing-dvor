package com.aiecel.gubernskytypography.repository;

import com.aiecel.gubernskytypography.model.OffSiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OffSiteUserRepository extends JpaRepository<OffSiteUser, Long> {
    Optional<OffSiteUser> findByUsernameAndRegistration(String username, String registration);
    boolean existsByUsernameAndRegistration(String username, String registration);
}
