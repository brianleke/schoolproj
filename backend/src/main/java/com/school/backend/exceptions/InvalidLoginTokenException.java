package com.school.backend.exceptions;

public class InvalidLoginTokenException extends RuntimeException{
    public InvalidLoginTokenException(){
        super("The login token is invalid.");
    }
}