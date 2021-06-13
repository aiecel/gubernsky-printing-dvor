package com.aiecel.gubernskytypography.controller;

import com.aiecel.gubernskytypography.dto.UrlDocumentDTO;
import com.aiecel.gubernskytypography.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/countPages")
    public int countPagesFromUrl(@RequestBody UrlDocumentDTO urlDocumentDTO) {
        return documentService.countPagesFromUrlDocument(urlDocumentDTO);
    }
}
