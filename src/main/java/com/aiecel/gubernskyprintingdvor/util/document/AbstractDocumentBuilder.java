package com.aiecel.gubernskyprintingdvor.util.document;

import com.aiecel.gubernskyprintingdvor.model.Document;
import lombok.Getter;

@Getter
public abstract class AbstractDocumentBuilder implements DocumentBuilder {
    private final Document document;

    public AbstractDocumentBuilder() {
        this.document = new Document();
    }

    @Override
    public DocumentBuilder setData(byte[] data) {
        document.setData(data);
        return this;
    }

    @Override
    public DocumentBuilder setTitle(String title) {
        document.setTitle(title);
        return this;
    }
}
