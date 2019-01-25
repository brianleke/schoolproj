package com.school.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidLoginTokenAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidLoginTokenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invalidLoginTokenHandler(InvalidLoginTokenException exception){
        return exception.getMessage();
    }
}
