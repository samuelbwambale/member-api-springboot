package com.example.memberapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private static final String ERROR = "Request failed due to: %s for URI: %S";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static ResponseEntity<String> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        StringBuilder errors = new StringBuilder();
        for (final FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.append(error.getField() + ": " + error.getDefaultMessage());
            errors.append("\n");
        }
        return new ResponseEntity<>(String.format(ERROR, errors, request.getRequestURI()), HttpStatus.BAD_REQUEST);

    }



}
