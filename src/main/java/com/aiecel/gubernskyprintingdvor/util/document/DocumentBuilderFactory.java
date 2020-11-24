package com.aiecel.gubernskyprintingdvor.util.document;

import com.aiecel.gubernskyprintingdvor.exception.ExtensionNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class DocumentBuilderFactory {
    public DocumentBuilder getDocumentBuilder(String extension) throws ExtensionNotSupportedException {
        switch (extension) {
            case "docx":
                return new DocxDocumentBuilder();

            default:
                throw new ExtensionNotSupportedException("File with extension " + extension + "are not supported");
        }
    }
}
