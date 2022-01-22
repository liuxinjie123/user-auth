package com.user.auth.exception;

import lombok.Data;

@Data
public class NotLoginException extends RuntimeException {
    private String message;

    public NotLoginException() {}

    public NotLoginException(String msg) {
        this.message = msg;
    }
}
