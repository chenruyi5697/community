package com.ccc.community.mapper;

import com.ccc.community.model.Question;
import com.ccc.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
}