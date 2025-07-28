package com.minigod.common.exceptions;

import com.minigod.common.result.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: guangjie.liao
 * @Date: 2022/10/29 08:54
 * @Description:
 */
@SuppressWarnings("ALL")
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultBody bindException(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException exception) {
        return ResultBody.fail(exception.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultBody methodArgumentNotValidException(HttpServletRequest request,
													  HttpServletResponse response,
													  MethodArgumentNotValidException exception) {
        return ResultBody.fail(exception.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultBody methodArgumentNotValidException(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     MissingServletRequestParameterException exception) {
        return ResultBody.fail(exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultBody exception(HttpServletRequest request,
                               HttpServletResponse response,
                               Exception exception) {
		exception.printStackTrace();
        return ResultBody.fail(exception.getMessage());
    }
}
