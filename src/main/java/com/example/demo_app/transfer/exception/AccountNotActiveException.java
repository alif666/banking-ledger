package com.example.demo_app.transfer.exception;

public class AccountNotActiveException extends RuntimeException{
    public AccountNotActiveException(){
        super("Both accounts must be active");
    }
}
