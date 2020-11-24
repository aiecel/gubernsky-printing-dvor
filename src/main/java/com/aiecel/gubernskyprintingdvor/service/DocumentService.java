package com.aiecel.gubernskyprintingdvor.service;

import com.aiecel.gubernskyprintingdvor.exception.FileDownloadException;
import com.aiecel.gubernskyprintingdvor.exception.DocumentBuildException;
import com.aiecel.gubernskyprintingdvor.exception.ExtensionNotSupportedException;
import com.aiecel.gubernskyprintingdvor.model.Document;

import java.net.URL;

public interface DocumentService extends DAOService<Document> {
    Document constructDocument(String title, String extension, URL dataUrl)
            throws DocumentBuildException, FileDownloadException, ExtensionNotSupportedException;

    Document constructDocument(String title, String extension, byte[] data)
            throws DocumentBuildException, ExtensionNotSupportedException;;
}
