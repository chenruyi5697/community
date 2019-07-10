package com.ccc.community.enums;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 10 18:09
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    public static String getNameByType(Integer type){
        for (NotificationTypeEnum typeEnum : NotificationTypeEnum.values()) {
            if (typeEnum.getType() == type){
                return typeEnum.getName();
            }
        }
        return "";
    }
}
