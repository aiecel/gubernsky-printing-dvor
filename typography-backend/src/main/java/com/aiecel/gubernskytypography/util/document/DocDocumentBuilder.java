package com.aiecel.gubernskytypography.util.document;

import com.aiecel.gubernskytypography.exception.DocumentBuildException;
import com.aiecel.gubernskytypography.model.Document;
import org.apache.poi.hwpf.HWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DocDocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public Document build() throws DocumentBuildException {
        if (getDocument().getData() == null || getDocument().getData().length == 0) {
            return getDocument();
        }

        try {
            HWPFDocument doc = new HWPFDocument(new ByteArrayInputStream(getDocument().getData()));
            getDocument().setPages(doc.getSummaryInformation().getPageCount());
            doc.close();
            return getDocument();
        } catch (IOException e) {
            throw new DocumentBuildException();
        }
    }
}
