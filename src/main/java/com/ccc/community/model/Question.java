package com.ccc.community.model;

import lombok.Data;

import javax.jnlp.IntegrationService;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 9:21
 */
@Data
public class Question {
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

}
