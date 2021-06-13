package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FeedbackDTO {
    String text;

    @JsonCreator
    public FeedbackDTO(@JsonProperty("text") String text) {
        this.text = text;
    }
}
