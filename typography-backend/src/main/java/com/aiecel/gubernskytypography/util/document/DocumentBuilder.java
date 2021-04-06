package com.aiecel.gubernskytypography.util.document;

import com.aiecel.gubernskytypography.exception.DocumentBuildException;
import com.aiecel.gubernskytypography.model.Document;

public interface DocumentBuilder {
    DocumentBuilder setData(byte[] data);
    DocumentBuilder setTitle(String title);
    Document build() throws DocumentBuildException;
}
