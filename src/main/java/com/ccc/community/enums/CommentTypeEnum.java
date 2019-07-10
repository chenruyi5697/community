package com.ccc.community.enums;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 13:57
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum typeEnum : CommentTypeEnum.values()){
            if (typeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
