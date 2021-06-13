package com.aiecel.gubernskytypography.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.net.URL;

@Value
public class UrlDocumentDTO {
    String title;
    String extension;
    URL url;

    @JsonCreator
    public UrlDocumentDTO(@JsonProperty("title") String title,
                          @JsonProperty("extension") String extension,
                          @JsonProperty("url") URL url) {
        this.title = title;
        this.extension = extension;
        this.url = url;
    }
}
