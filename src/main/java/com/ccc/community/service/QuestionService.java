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

    PageDTO questionList(Long accountId, Integer page, Integer size);

    QuestionDTO getById(Long id);

    void updateOrInsert(Question question);

    Question selectById(Long id);

    void incView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
