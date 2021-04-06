package com.aiecel.gubernskytypography.repository;

import com.aiecel.gubernskytypography.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
