package com.ccc.community.service.impl;

import com.ccc.community.dto.PageDTO;
import com.ccc.community.dto.QuestionDTO;
import com.ccc.community.mapper.QuestionMapper;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.Question;
import com.ccc.community.model.User;
import com.ccc.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 11:39
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public PageDTO pageInfo(Integer page , Integer size) {
        PageDTO pageDTO = new PageDTO();
            if (page < 1){
                page = 1;
            }
            //分页查询偏移量
            Integer offset = size * (page - 1);
            Integer totalCount = questionMapper.selectCount();
            Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
            if (page > totalPage){
                page = totalPage;
            }
            pageDTO.setPage(page);
            pageDTO.setTotalPage(totalPage);
            //修改判断信息
            pageDTO.setPageInfo(totalCount , page , size);
            List<QuestionDTO> list = new ArrayList<>();
            List<Question> questionList = questionMapper.selectListPage(offset , size);
            if (null != questionList && questionList.size() != 0){
                for (Question question : questionList) {
                    User user = userMapper.findById(question.getCreator());
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(question , questionDTO);
                    questionDTO.setUser(user);
                    list.add(questionDTO);
                }
            pageDTO.setList(list);
            return pageDTO;
        }else {
            return null;
        }
    }

    @Override
    public PageDTO questionList(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        if (page < 1){
            page = 1;
        }
        //分页查询偏移量
        Integer offset = size * (page - 1);
        Integer totalCount = questionMapper.selectCountById(userId);
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        if (page > totalPage){
            page = totalPage;
        }
        pageDTO.setPage(page);
        pageDTO.setTotalPage(totalPage);
        //修改判断信息
        pageDTO.setPageInfo(totalCount , page , size);
        List<QuestionDTO> list = new ArrayList<>();
        List<Question> questionList = questionMapper.selectListByCreator(userId , offset , size);
        if (null != questionList && questionList.size() != 0){
            for (Question question : questionList) {
                User user = userMapper.findById(userId);
                System.out.println(user.getToken()+">>>>>><<<<<");
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question , questionDTO);
                questionDTO.setUser(user);
                list.add(questionDTO);
            }
            pageDTO.setList(list);
            return pageDTO;
        }else {
            return null;
        }
    }

    @Override
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectById(id);
        System.out.println("que impl "+question);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question , questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        System.out.println("que impl "+questionDTO);
        return questionDTO;
    }

    @Override
    public void updateOrInsert(Question question) {
        if (null == question.getId()){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertQuestion(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updateQuestion(question);
        }
    }

    @Override
    public Question selectById(Integer id) {
        Question question = questionMapper.selectById(id);
        return question;
    }
}
