package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class OffSiteUserDTO {
    String username;
    String displayName;
    String registration;

    public OffSiteUserDTO(@JsonProperty("username") String username,
                          @JsonProperty("displayName") String displayName,
                          @JsonProperty("registration") String registration) {
        this.username = username;
        this.displayName = displayName;
        this.registration = registration;
    }
}
