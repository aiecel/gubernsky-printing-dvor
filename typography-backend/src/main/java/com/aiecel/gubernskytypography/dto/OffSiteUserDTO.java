package com.aiecel.gubernskytypography.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class OffSiteUserDTO {
    @NotNull
    private final String username;

    @NotNull
    private final String displayName;

    @NotNull
    private final String registration;

    @JsonCreator
    public OffSiteUserDTO(@JsonProperty("username") String username,
                          @JsonProperty("displayName") String displayName,
                          @JsonProperty("registration") String registration) {
        this.username = username;
        this.displayName = displayName;
        this.registration = registration;
    }
}
