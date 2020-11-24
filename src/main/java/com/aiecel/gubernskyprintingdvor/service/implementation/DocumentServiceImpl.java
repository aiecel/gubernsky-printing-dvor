package com.aiecel.gubernskyprintingdvor.service.implementation;

import com.aiecel.gubernskyprintingdvor.exception.FileDownloadException;
import com.aiecel.gubernskyprintingdvor.exception.DocumentBuildException;
import com.aiecel.gubernskyprintingdvor.exception.ExtensionNotSupportedException;
import com.aiecel.gubernskyprintingdvor.model.Document;
import com.aiecel.gubernskyprintingdvor.repository.DocumentRepository;
import com.aiecel.gubernskyprintingdvor.service.DocumentService;
import com.aiecel.gubernskyprintingdvor.util.FileDownloader;
import com.aiecel.gubernskyprintingdvor.util.document.DocumentBuilderFactory;
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
