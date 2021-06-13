package com.aiecel.gubernskytypography.util;

import com.aiecel.gubernskytypography.exception.PageCountingException;
import org.apache.poi.hwpf.HWPFDocument;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class DocPageCounter implements PageCounter {
    @Override
    public String getSupportedExtension() {
        return "doc";
    }

    @Override
    public int count(byte[] data) {
        try (HWPFDocument doc = new HWPFDocument(new ByteArrayInputStream(data))) {
            return doc.getSummaryInformation().getPageCount();
        } catch (IOException e) {
            throw new PageCountingException();
        }
    }
}
