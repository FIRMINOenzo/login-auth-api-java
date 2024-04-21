package com.example.loginauthapi.exceptions;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("Invalid password.");
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
