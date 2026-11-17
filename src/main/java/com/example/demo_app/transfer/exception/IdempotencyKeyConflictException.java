package com.example.demo_app.transfer.exception;

public class IdempotencyKeyConflictException extends RuntimeException{
    public IdempotencyKeyConflictException(){
        super("Idempotency key conflict");
    }
}
