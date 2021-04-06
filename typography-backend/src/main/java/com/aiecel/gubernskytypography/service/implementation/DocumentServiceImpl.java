package com.aiecel.gubernskytypography.service.implementation;

import com.aiecel.gubernskytypography.exception.FileDownloadException;
import com.aiecel.gubernskytypography.exception.DocumentBuildException;
import com.aiecel.gubernskytypography.exception.ExtensionNotSupportedException;
import com.aiecel.gubernskytypography.model.Document;
import com.aiecel.gubernskytypography.repository.DocumentRepository;
import com.aiecel.gubernskytypography.service.DocumentService;
import com.aiecel.gubernskytypography.util.FileDownloader;
import com.aiecel.gubernskytypography.util.document.DocumentBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentBuilderFactory documentBuilderFactory;
    private final FileDownloader fileDownloader;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentBuilderFactory documentBuilderFactory,
                               FileDownloader fileDownloader) {
        this.documentRepository = documentRepository;
        this.documentBuilderFactory = documentBuilderFactory;
        this.fileDownloader = fileDownloader;
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document constructDocument(String title, String extension, URL dataUrl)
            throws DocumentBuildException, FileDownloadException, ExtensionNotSupportedException {
        return constructDocument(title, extension, fileDownloader.download(dataUrl));
    }

    @Override
    public Document constructDocument(String title, String extension, byte[] data)
            throws DocumentBuildException, ExtensionNotSupportedException {
        return documentBuilderFactory.getDocumentBuilder(extension)
                .setTitle(title)
                .setData(data)
                .build();
    }
}
