package com.example.demo_app.account.exception;

public class AccountOwnershipException extends RuntimeException{
    public AccountOwnershipException(){
        super("User is not authorized to transfer money");
    }
}
