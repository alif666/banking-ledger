package com.example.demo_app.auth.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{
    public EmailAlreadyRegisteredException(){
        super("Email is already registered");
    }
}
