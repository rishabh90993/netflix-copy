package com.example.Netflix.Exceptions;

public class InvalidProfileException extends RuntimeException{

    public InvalidProfileException(String message){
        super(message);
    }

}
