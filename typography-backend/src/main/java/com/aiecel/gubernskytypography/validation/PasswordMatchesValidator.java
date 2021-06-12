package com.aiecel.gubernskytypography.validation;

import com.aiecel.gubernskytypography.dto.RegisterFormDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterFormDTO registerFormDTO = (RegisterFormDTO) o;
        return registerFormDTO.getPassword().equals(registerFormDTO.getConfirmPassword());
    }
}
