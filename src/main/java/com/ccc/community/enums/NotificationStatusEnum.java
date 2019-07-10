package com.ccc.community.enums;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 10 18:18
 */
public enum NotificationStatusEnum {
    UNREAD(0) , READ(1);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }
}
