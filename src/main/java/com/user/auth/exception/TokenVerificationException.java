package com.user.auth.exception;

import lombok.Data;

@Data
public class TokenVerificationException extends RuntimeException{
    private String message;

    public TokenVerificationException() {}

    public TokenVerificationException(String message) {
        this.message = message;
    }
}
