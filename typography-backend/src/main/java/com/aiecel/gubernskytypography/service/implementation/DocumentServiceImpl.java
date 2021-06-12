package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.dto.UrlDocumentDTO;
import com.aiecel.gubernskytypography.service.DocumentService;
import com.aiecel.gubernskytypography.util.FileDownloader;
import com.aiecel.gubernskytypography.util.PageCounter;
import com.aiecel.gubernskytypography.util.PageCounterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final PageCounterFactory pageCounterFactory;
    private final FileDownloader fileDownloader;

    @Override
    public int countPagesFromUrlDocument(UrlDocumentDTO urlDocument) {
        PageCounter pageCounter = pageCounterFactory.getPageCounter(urlDocument.getExtension());
        byte[] data = fileDownloader.download(urlDocument.getUrl());
        return pageCounter.count(data);
    }
}
