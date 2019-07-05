package com.ccc.community.controller;

import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
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

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User uesr = userMapper.findByToken(token);
                if (null != uesr){
                    request.getSession().setAttribute("user" , uesr);
                }
                break;
            }
        }
        return "index";
    }
}
