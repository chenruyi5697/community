package com.ccc.community.dto;

import lombok.Data;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 04 20:46
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
