package com.school.backend.exceptions;

public class GenericNotFoundException extends RuntimeException{
    public GenericNotFoundException(String message){
        super(message);
    }
}
