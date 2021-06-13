package com.aiecel.gubernskytypography.util;

import com.aiecel.gubernskytypography.exception.PageCountingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PdfPageCounter implements PageCounter {
    @Override
    public String getSupportedExtension() {
        return "pdf";
    }

    @Override
    public int count(byte[] data) {
        try (PDDocument doc = PDDocument.load(data)) {
            return doc.getNumberOfPages();
        } catch (IOException e) {
            throw new PageCountingException();
        }
    }
}
