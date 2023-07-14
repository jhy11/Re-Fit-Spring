package com.umc.refit.exception.clothe;

import com.umc.refit.exception.ExceptionType;
import lombok.Getter;

@Getter
public class ClotheException extends RuntimeException {

    private ExceptionType exceptionType;
    private int code;
    private String errorMessage;
}
