package com.aiecel.gubernskytypography.util.document;

import com.aiecel.gubernskytypography.exception.ExtensionNotSupportedException;
import org.springframework.stereotype.Component;

@Component
public class DocumentBuilderFactory {
    public DocumentBuilder getDocumentBuilder(String extension) throws ExtensionNotSupportedException {
        switch (extension.toLowerCase()) {
            case "docx":
                return new DocxDocumentBuilder();

            case "doc":
                return new DocDocumentBuilder();

            case "pdf":
                return new PdfDocumentBuilder();

            default:
                throw new ExtensionNotSupportedException("File with extension " + extension + "are not supported");
        }
    }
}
