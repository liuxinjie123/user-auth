package com.user.auth.exception;

import lombok.Data;

@Data
public class TokenAuthExpiredException extends RuntimeException {
    private String message;

    public TokenAuthExpiredException() {}

    public TokenAuthExpiredException(String msg) {
        this.message = msg;
    }
}
