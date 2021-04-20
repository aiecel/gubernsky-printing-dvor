package com.aiecel.gubernskytypography.exception;

import com.aiecel.gubernskytypography.model.User;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
