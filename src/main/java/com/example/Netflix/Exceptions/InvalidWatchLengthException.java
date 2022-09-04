package com.example.Netflix.Exceptions;

public class InvalidWatchLengthException extends RuntimeException{

    public InvalidWatchLengthException(String message){
        super(message);
    }

}
