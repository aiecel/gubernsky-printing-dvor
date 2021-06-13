package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class DocumentDTO {
    @NotNull
    String title;

    String url;

    @Min(1)
    int pages;

    @JsonCreator
    public DocumentDTO(@JsonProperty("title") String title,
                       @JsonProperty("url") String url,
                       @JsonProperty("pages") int pages) {
        this.title = title;
        this.url = url;
        this.pages = pages;
    }
}
