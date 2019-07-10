package com.ccc.community.controller;

import com.ccc.community.dto.CommentDTO;
import com.ccc.community.dto.QuestionDTO;
import com.ccc.community.enums.CommentTypeEnum;
import com.ccc.community.service.CommentService;
import com.ccc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 19:04
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentList =  commentService.listByTagetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        List<QuestionDTO> relatedQuestion = questionService.selectRelated(questionDTO);
        model.addAttribute("question" , questionDTO);
        model.addAttribute("comments" , commentList);
        model.addAttribute("relatedQuestion" , relatedQuestion);
        return "question";
    }
}
