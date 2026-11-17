package com.example.demo_app.auth.exception;

public class DefaultRoleNotFoundException extends RuntimeException {
    public DefaultRoleNotFoundException(){
        super("Default Role Not Found");
    }
}
