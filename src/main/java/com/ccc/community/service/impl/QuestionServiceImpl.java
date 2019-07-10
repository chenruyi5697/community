package com.ccc.community.service.impl;

import com.ccc.community.dto.PageDTO;
import com.ccc.community.dto.QuestionDTO;
import com.ccc.community.exception.CustomizeException;
import com.ccc.community.exception.impl.CustomizeErrorCodeImpl;
import com.ccc.community.mapper.QuestionExtMapper;
import com.ccc.community.mapper.QuestionMapper;
import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.Question;
import com.ccc.community.model.QuestionExample;
import com.ccc.community.model.User;
import com.ccc.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 11:39
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public PageDTO pageInfo(Integer page , Integer size) {
        PageDTO<QuestionDTO> pageDTO = new PageDTO();
            if (page < 1){
                page = 1;
            }
            //分页查询偏移量
            Integer offset = size * (page - 1);
            QuestionExample example = new QuestionExample();
            Integer totalCount = (int) questionMapper.countByExample(example);
            Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
            if (page > totalPage){
                page = totalPage;
            }
            pageDTO.setPage(page);
            pageDTO.setTotalPage(totalPage);
            //修改判断信息
            pageDTO.setPageInfo(totalCount , page , size);
            List<QuestionDTO> list = new ArrayList<>();
            example.setOrderByClause("gmt_modified desc");
            List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example , new RowBounds(offset , size));
            if (null != questionList && questionList.size() != 0){
                for (Question question : questionList) {
                    User user = userMapper.selectByPrimaryKey(question.getCreator());
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(question , questionDTO);
                    System.out.println(questionDTO);
                    questionDTO.setUser(user);
                    list.add(questionDTO);
                }
            pageDTO.setData(list);
            return pageDTO;
        }else {
            return null;
        }
    }

    @Override
    public PageDTO questionList(Long userId, Integer page, Integer size) {
        PageDTO<QuestionDTO> pageDTO = new PageDTO();
        if (page < 1){
            page = 1;
        }
        //分页查询偏移量
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        if (page > totalPage){
            page = totalPage;
        }
        pageDTO.setPage(page);
        pageDTO.setTotalPage(totalPage);
        //修改判断信息
        pageDTO.setPageInfo(totalCount , page , size);
        List<QuestionDTO> list = new ArrayList<>();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset , size));
        if (null != questionList && questionList.size() != 0){
            for (Question question : questionList) {
                User user = userMapper.selectByPrimaryKey(userId);
                System.out.println(user.getToken()+">>>>>><<<<<");
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question , questionDTO);
                questionDTO.setUser(user);
                list.add(questionDTO);
            }
            pageDTO.setData(list);
            return pageDTO;
        }else {
            return null;
        }
    }

    @Override
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (null == question){
            throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question , questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void updateOrInsert(Question question) {
        if (null == question.getId()){
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            int i = questionMapper.updateByPrimaryKey(question);
            if (i == 0){
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public Question selectById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        return question;
    }

    @Override
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if ( StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(question1 -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question1 , questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
