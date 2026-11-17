package com.example.demo_app.transfer.exception;

public class AccountSourceNotValidException extends RuntimeException{
    public AccountSourceNotValidException(){
        super("From Account is not valid");
    }
}
