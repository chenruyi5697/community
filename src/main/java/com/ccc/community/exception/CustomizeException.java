package com.ccc.community.exception;

import com.ccc.community.exception.impl.CustomizeErrorCodeImpl;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 10:38
 */
public class CustomizeException extends RuntimeException {
    private Integer code;
    private String message;


    public CustomizeException(CustomizeErrorCodeImpl errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
