package com.ccc.community.exception.impl;

import com.ccc.community.exception.CustomizeErrorCode;
import com.ccc.community.exception.CustomizeException;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 10:34
 */
public enum  CustomizeErrorCodeImpl implements CustomizeErrorCode {
    QUESTION_NOT_FOUND(2001 , "你寻找的问题不存在，请重试或查询其他关键字"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003 ,"当前操作需要登陆，请登陆后重试"),
    SYS_ERROR(2004 , "当前系统服务繁忙，请稍后重新尝试"),
    TYPE_PARAM_WRONG(2005, "评论类型错误"),
    COMMENT_NO_FOUNF(2006,"回复评论不存在了"),
    CONTENT_IS_EMPTY(2007 , "输入内容不能为空"),
    NOTIFICATION_NOT_FOUND(2008,"通知不存在"),
    READ_NOTIFICATION_FAIL(2009,"读取通知信息失败");

    CustomizeErrorCodeImpl(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    private Integer code;
    private String message;
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode() {
        return code;
    }


}
