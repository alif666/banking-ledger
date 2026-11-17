package com.example.demo_app.transfer.exception;

public class UnsupportedSameAccountException extends RuntimeException{
    public UnsupportedSameAccountException(){
        super("Unsupported same account transfer");
    }
}
