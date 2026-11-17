package com.example.demo_app.transfer.exception;

public class UnsupportedCurrencyMatchBetweenAccountsException extends RuntimeException{
    public UnsupportedCurrencyMatchBetweenAccountsException(){
        super("Currency code doesn't match");
    }
}
