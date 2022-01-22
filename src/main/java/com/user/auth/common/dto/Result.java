package com.user.auth.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public Result() {}


    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    /**
     * 成功
     */
    public static <T> Result<T> success() {
        return new Result<>(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG);
    }
    public static <T> Result<T> success(String msg) {
        return new Result<>(CommonConstants.SUCCESS_CODE, msg);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS_MSG, data);
    }

    /**
     * 异常
     */
    public static <T> Result<T> error() {
        return new Result<>(CommonConstants.ERROR_CODE, CommonConstants.ERROR_MSG);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(CommonConstants.ERROR_CODE, msg);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }
}
