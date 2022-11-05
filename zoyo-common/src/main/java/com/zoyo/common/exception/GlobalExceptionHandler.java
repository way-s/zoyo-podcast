package com.zoyo.common.exception;

import com.zoyo.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: mxx
 * @Description: 异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常
     *
     * @param e ApiException
     * @return Result
     */
    @ExceptionHandler(value = ApiException.class)
    public Result<Object> apiExceptionHandler(ApiException e) {
        if (e.getErrorCode() != null) {
            return Result.fail(e.getErrorCode());
        }
        log.error("e.getMessage() {}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    /**
     * validate失败后抛出的异常
     *
     * @param e MethodArgumentNotValidException
     * @return Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        return exceptionCommonMethod(e);
    }

    /**
     * / @Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler(value = BindException.class)
    public Result<Object> bindExceptionHandler(BindException e) {
        return exceptionCommonMethod(e);
    }

    /**
     * 处理空指针的异常
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionHandler(Exception e) {
        log.error("处理异常，原因是，{},{}", e.getMessage(), e);
        return Result.fail();
    }

    private Result<Object> exceptionCommonMethod(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String msg = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                msg = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Result.validateFailed(msg);
    }

}
