package com.ccc.community.controller;

import com.ccc.community.dto.PageDTO;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.User;
import com.ccc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 16:48
 */
@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action ,
                          @RequestParam(value = "page" , defaultValue = "1") Integer page,
                          @RequestParam(value = "size" , defaultValue = "8") Integer size,
                          HttpServletRequest request ,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (null == user){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section" , action);
            model.addAttribute("sectionName" , "我的提问");
        }else if("replies".equals(action)) {
            model.addAttribute("section" , action);
            model.addAttribute("sectionName" , "我的回复");
        }
        PageDTO pageInfo = questionService.questionList(user.getId(), page, size);
        model.addAttribute("pageInfo" , pageInfo);
        return "profile";
    }
}
