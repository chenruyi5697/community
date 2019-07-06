package com.ccc.community.dto;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.ccc.community.model.User;
import lombok.Data;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 11:37
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
