package com.ccc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 04 17:29
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
