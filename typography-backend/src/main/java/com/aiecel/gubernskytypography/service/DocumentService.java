package com.aiecel.gubernskytypography.service;

import com.aiecel.gubernskytypography.exception.FileDownloadException;
import com.aiecel.gubernskytypography.exception.DocumentBuildException;
import com.aiecel.gubernskytypography.exception.ExtensionNotSupportedException;
import com.aiecel.gubernskytypography.model.Document;

import java.net.URL;

public interface DocumentService extends DAOService<Document> {
    Document constructDocument(String title, String extension, URL dataUrl)
            throws DocumentBuildException, FileDownloadException, ExtensionNotSupportedException;

    Document constructDocument(String title, String extension, byte[] data)
            throws DocumentBuildException, ExtensionNotSupportedException;;
}
