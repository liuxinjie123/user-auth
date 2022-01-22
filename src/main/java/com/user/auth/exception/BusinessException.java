package com.user.auth.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private String message;

    public BusinessException() {}

    public BusinessException(String msg) {
        this.message = msg;
    }

}