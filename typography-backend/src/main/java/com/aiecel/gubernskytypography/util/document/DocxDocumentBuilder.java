package com.aiecel.gubernskytypography.util.document;

import com.aiecel.gubernskytypography.exception.DocumentBuildException;
import com.aiecel.gubernskytypography.model.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DocxDocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public Document build() throws DocumentBuildException {
        if (getDocument().getData() == null || getDocument().getData().length == 0) {
            return getDocument();
        }

        try {
            XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(getDocument().getData()));
            getDocument().setPages(doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages());
            doc.close();
            return getDocument();
        } catch (IOException e) {
            throw new DocumentBuildException();
        }
    }
}
