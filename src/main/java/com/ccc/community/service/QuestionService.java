package com.ccc.community.service;

import com.ccc.community.dto.PageDTO;
import com.ccc.community.dto.QuestionDTO;
import com.ccc.community.model.Question;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 11:38
 */
public interface QuestionService {
    PageDTO pageInfo(Integer page , Integer size);

    PageDTO questionList(Integer accountId, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void updateOrInsert(Question question);

    Question selectById(Integer id);
}
