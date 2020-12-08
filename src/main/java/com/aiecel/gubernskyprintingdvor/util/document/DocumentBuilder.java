package com.aiecel.gubernskyprintingdvor.util.document;

import com.aiecel.gubernskyprintingdvor.exception.DocumentBuildException;
import com.aiecel.gubernskyprintingdvor.model.Document;

public interface DocumentBuilder {
    DocumentBuilder setData(byte[] data);
    DocumentBuilder setTitle(String title);
    Document build() throws DocumentBuildException;
}
