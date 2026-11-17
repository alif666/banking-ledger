package com.example.demo_app.account.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(){
        super("Insufficient Fund Exception");
    }
}
