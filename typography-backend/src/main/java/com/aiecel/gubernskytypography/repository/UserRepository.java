package com.aiecel.gubernskytypography.repository;

import com.aiecel.gubernskytypography.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
