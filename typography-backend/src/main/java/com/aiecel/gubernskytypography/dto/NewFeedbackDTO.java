package com.aiecel.gubernskytypography.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NewFeedbackDTO {
    @NotBlank
    String text;

    @JsonCreator
    public NewFeedbackDTO(@JsonProperty("text") String text) {
        this.text = text;
    }
}
