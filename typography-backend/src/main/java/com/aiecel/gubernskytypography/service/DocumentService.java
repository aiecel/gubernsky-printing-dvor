package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.dto.UrlDocumentDTO;

public interface DocumentService {
    int countPagesFromUrlDocument(UrlDocumentDTO urlDocument);
}
