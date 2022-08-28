package com.example.Netflix.Exceptions;

public class AuthenticationFaliureException extends RuntimeException{
    public AuthenticationFaliureException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
