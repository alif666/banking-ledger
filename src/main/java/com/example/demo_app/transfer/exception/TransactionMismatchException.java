package com.example.demo_app.transfer.exception;

public class TransactionMismatchException extends  RuntimeException {
    public TransactionMismatchException(){
        super("Transfer records don't match for retry");
    }
}
