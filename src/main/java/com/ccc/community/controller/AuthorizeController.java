package com.ccc.community.controller;

import com.ccc.community.dto.AccessTokenDTO;
import com.ccc.community.dto.GithubUser;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.User;
import com.ccc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 04 19:25
 */
@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider provider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code ,
                           @RequestParam(name = "state") String state ,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = provider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = provider.getUser(accessToken);
        if (null != githubUser){
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
            //登陆成功，写cookie
            response.addCookie(new Cookie("token" , token));
            return "redirect:/";
        }else {
            //登陆失败
            return "redirect:/";
        }
    }
}
