package com.example.blogjava.crypto;

public class ApiException extends RuntimeException{
    private String message;
    public ApiException(String message){
        super(message);
    }
}
