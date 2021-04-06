package com.aiecel.gubernskytypography.repository;

import com.aiecel.gubernskytypography.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
