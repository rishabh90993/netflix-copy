package com.example.Netflix.Exceptions;

public class OtpNotFoundException extends RuntimeException{
   public OtpNotFoundException(String message){
      super(message);
    }
}
