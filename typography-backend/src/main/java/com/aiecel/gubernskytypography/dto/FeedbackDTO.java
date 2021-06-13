package com.aiecel.gubernskytypography.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class FeedbackDTO {
    long id;
    String text;
    ZonedDateTime sendingDateTime;

    @JsonCreator
    public FeedbackDTO(@JsonProperty("id") long id,
                       @JsonProperty("text") String text,
                       @JsonProperty("sendingDateTime") ZonedDateTime sendingDateTime) {
        this.id = id;
        this.text = text;
        this.sendingDateTime = sendingDateTime;
    }
}
