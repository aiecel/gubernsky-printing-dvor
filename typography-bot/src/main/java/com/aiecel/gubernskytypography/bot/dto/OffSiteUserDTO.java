package com.aiecel.gubernskytypography.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OffSiteUserDTO {
    private final String username;
    private final String displayName;
    private final String registration;

    public OffSiteUserDTO(@JsonProperty("username") String username,
                          @JsonProperty("displayName") String displayName,
                          @JsonProperty("registration") String registration) {
        this.username = username;
        this.displayName = displayName;
        this.registration = registration;
    }
}
