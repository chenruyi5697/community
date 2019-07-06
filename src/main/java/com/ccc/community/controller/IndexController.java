package com.ccc.community.controller;

import com.ccc.community.dto.PageDTO;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 04 17:29
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request ,
                        @RequestParam(value = "page" , defaultValue = "1") Integer page,
                        @RequestParam(value = "size" , defaultValue = "8") Integer size,
                        Model model){

        PageDTO pageInfo = questionService.pageInfo(page , size);
        if (null != pageInfo){
            model.addAttribute("pageInfo" , pageInfo);
        }
        return "index";
    }
}
