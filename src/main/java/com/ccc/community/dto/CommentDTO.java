package com.ccc.community.dto;

import com.ccc.community.model.User;
import lombok.Data;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 18:12
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
