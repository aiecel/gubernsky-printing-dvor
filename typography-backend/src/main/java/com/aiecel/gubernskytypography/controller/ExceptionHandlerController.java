package com.aiecel.gubernskytypography.controller;

import com.aiecel.gubernskytypography.exception.UserAlreadyRegisteredException;
import com.aiecel.gubernskytypography.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessageBuilder.append(errorMessageBuilder.length() > 0 ? "; " : "");
            errorMessageBuilder.append(error.getDefaultMessage());
        });
        return new ResponseEntity<>(new ExceptionResponse(errorMessageBuilder.toString()), headers, status);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected ResponseEntity<ExceptionResponse> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e) {
        return new ResponseEntity<>(ExceptionResponse.fromException(e), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(ExceptionResponse.fromException(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(ExceptionResponse.fromException(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    private static class ExceptionResponse {
        private String errorMessage;

        public static ExceptionResponse fromException(Exception e) {
            return new ExceptionResponse(e.getMessage());
        }
    }
}
