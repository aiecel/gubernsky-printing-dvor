package com.aiecel.gubernskyprintingdvor.repository;

import com.aiecel.gubernskyprintingdvor.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
