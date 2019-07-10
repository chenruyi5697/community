package com.ccc.community.controller;

import com.ccc.community.cache.TagCache;
import com.ccc.community.mapper.QuestionMapper;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.Question;
import com.ccc.community.model.User;
import com.ccc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 8:37
 */
@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title" , title);
        model.addAttribute("description" , description);
        model.addAttribute("tag" , tag);
        model.addAttribute("tags", TagCache.get());
        User user = (User) request.getSession().getAttribute("user");
        if (null == user){
            model.addAttribute("msg" , "用户未登录");
            return "publish";
        }
        if(null == title || title.equals("")){
            model.addAttribute("msg" , "标题不能为空");
            return "publish";
        }
        if(null == description || description.equals("")){
            model.addAttribute("msg" , "问题描述不能为空");
            return "publish";
        }
        if(null == tag || tag.equals("")){
            model.addAttribute("msg" , "标签不能为空");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.updateOrInsert(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,
                       Model model){
        Question question = questionService.selectById(id);
        model.addAttribute("title" , question.getTitle());
        model.addAttribute("description" , question.getDescription());
        model.addAttribute("tag" , question.getTag());
        model.addAttribute("id" , question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

}
