package com.aiecel.gubernskytypography.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserDTO {
    String username;
    String displayName;

    @JsonCreator
    public UserDTO(@JsonProperty("username") String username,
                   @JsonProperty("displayName") String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
}
