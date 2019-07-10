package com.ccc.community.controller;

import com.ccc.community.dto.CommentCreateDTO;
import com.ccc.community.dto.CommentDTO;
import com.ccc.community.dto.ResultDTO;
import com.ccc.community.enums.CommentTypeEnum;
import com.ccc.community.exception.impl.CustomizeErrorCodeImpl;
import com.ccc.community.model.Comment;
import com.ccc.community.model.User;
import com.ccc.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 12:21
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (null == user){
            return ResultDTO.errorof(CustomizeErrorCodeImpl.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorof(CustomizeErrorCodeImpl.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0L);
        commentService.insert(comment , user);
        return ResultDTO.okof();
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List> comments(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTagetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
