package com.example.Netflix.Exceptions;

public class DependencyFaliureException extends RuntimeException{
    public DependencyFaliureException(Throwable cause){
        super(cause);
    }
}
