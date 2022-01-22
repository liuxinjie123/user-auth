package com.user.auth.config;

import com.user.auth.common.dto.Result;
import com.user.auth.exception.BusinessException;
import com.user.auth.exception.NotLoginException;
import com.user.auth.exception.TokenAuthExpiredException;
import com.user.auth.exception.TokenVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler({BusinessException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result businessExceptionHandler(Exception e) {
        log.warn("BusinessException：{}.", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result missingServletRequestParameterExceptionHandler(Exception e) {
        log.warn("MissingServletRequestParameterException: {}.", e);
        return Result.error(e.getMessage());
    }

    /**
     * 用户 token 过期
     */
    @ExceptionHandler(value = TokenAuthExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result tokenExpiredExceptionHandler(Exception e){
        log.warn("用户 token 过期, {}.", e);
        return Result.error(e.getMessage());
    }

    /**
     * 未登录Exception
     */
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result notLoginExceptionHandler(Exception e) {
        log.warn("未登录，{}.", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = TokenVerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result tokenVerificationExceptionHandler(Exception e) {
        log.warn("token 非法, {}.", e);
        return Result.error(e.getMessage());
    }

}
