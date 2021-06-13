package com.aiecel.gubernskytypography.util;

import com.aiecel.gubernskytypography.exception.PageCountingException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class DocxPageCounter implements PageCounter {
    @Override
    public String getSupportedExtension() {
        return "docx";
    }

    @Override
    public int count(byte[] data) {
        try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(data))) {
            return doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        } catch (IOException e) {
            throw new PageCountingException();
        }
    }
}
