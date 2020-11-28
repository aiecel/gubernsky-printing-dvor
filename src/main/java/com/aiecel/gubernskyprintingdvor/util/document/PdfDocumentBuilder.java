package com.aiecel.gubernskyprintingdvor.util.document;

import com.aiecel.gubernskyprintingdvor.exception.DocumentBuildException;
import com.aiecel.gubernskyprintingdvor.model.Document;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class PdfDocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public Document build() throws DocumentBuildException {
        if (getDocument().getData() == null || getDocument().getData().length == 0) {
            return getDocument();
        }

        try {
            PDDocument doc = PDDocument.load(getDocument().getData());
            getDocument().setPages(doc.getNumberOfPages());
            doc.close();
            return getDocument();
        } catch (IOException e) {
            throw new DocumentBuildException();
        }
    }
}
