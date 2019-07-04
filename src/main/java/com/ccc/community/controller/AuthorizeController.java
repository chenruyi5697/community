package com.ccc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 04 19:25
 */
@Controller
public class AuthorizeController {
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code ,
                           @RequestParam(name = "state") String state){
        return "index";
    }
}
