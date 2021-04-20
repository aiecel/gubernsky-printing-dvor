package com.aiecel.gubernskytypography.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserDTO {
    private final String username;

    @JsonCreator
    public UserDTO(@JsonProperty("username") String username) {
        this.username = username;
    }
}
