package com.ccc.community.service;

import com.ccc.community.dto.CommentDTO;
import com.ccc.community.enums.CommentTypeEnum;
import com.ccc.community.model.Comment;
import com.ccc.community.model.User;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 12:21
 */
public interface CommentService {
    void insert(Comment comment, User user);
    List<CommentDTO> listByTagetId(Long id, CommentTypeEnum question);

}
