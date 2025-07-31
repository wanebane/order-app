package com.rivaldy.orderapp.exception;

public class InsufficientException extends RuntimeException{

    public InsufficientException(String message){
        super(message);
    }
}
