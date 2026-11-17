package com.example.demo_app.currency.exception;

public class UnsupportedCurrencyCodeException extends RuntimeException{
    public UnsupportedCurrencyCodeException(){
        super("Unsupported Currency Code");
    }
}
