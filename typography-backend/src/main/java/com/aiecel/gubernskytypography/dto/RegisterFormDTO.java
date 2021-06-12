package com.aiecel.gubernskytypography.dto;

import com.aiecel.gubernskytypography.validation.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@PasswordMatches
public class RegisterFormDTO {
    @NotBlank(message = "Укажите логинъ")
    String username;

    String displayName;

    @NotBlank(message = "Укажите кодовое слово")
    String password;

    @NotBlank(message = "Повторите кодовое слово во избежание курьёза")
    String confirmPassword;

    @JsonCreator
    public RegisterFormDTO(@JsonProperty("username") String username,
                           @JsonProperty("displayName") String displayName,
                           @JsonProperty("password") String password,
                           @JsonProperty("confirmPassword") String confirmPassword) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
