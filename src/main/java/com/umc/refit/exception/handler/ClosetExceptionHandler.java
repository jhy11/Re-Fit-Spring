package com.umc.refit.exception.handler;

import com.umc.refit.exception.ErrorResult;
import com.umc.refit.exception.clothe.ClosetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ClosetExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClosetException.class)
    public ErrorResult ClotheExceptionHandle(ClosetException e, HttpServletRequest request) {
        log.error("[CustomException] url: {} | errorType: {} | errorMessage: {} | cause Exception: ",
                request.getRequestURL(), e.getExceptionType(), e.getMessage(), e.getCause());
        return new ErrorResult(String.valueOf(e.getCode()), e.getErrorMessage());
    }
}
