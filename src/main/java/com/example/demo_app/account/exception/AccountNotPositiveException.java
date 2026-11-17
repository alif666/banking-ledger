package com.example.demo_app.account.exception;

public class AccountNotPositiveException extends RuntimeException{
    public AccountNotPositiveException(){
        super("Amount must be positive");
    }
}
