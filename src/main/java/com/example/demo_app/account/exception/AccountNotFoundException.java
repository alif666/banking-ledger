package com.example.demo_app.account.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(){
        super("Account not found");
    }
}
