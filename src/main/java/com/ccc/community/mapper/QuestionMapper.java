package com.ccc.community.mapper;

import com.ccc.community.dto.QuestionDTO;
import com.ccc.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 9:26
 */
@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question(title,description,gmt_create,gmt_modified,creator,tag) VALUES(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insertQuestion(Question question);

    @Select("SELECT * FROM question")
    List<Question> selectList();

    @Select("SELECT COUNT(*) FROM question")
    Integer selectCount();

    @Select("SELECT * FROM question LIMIT #{page} , #{size}")
    List<Question> selectListPage(@Param("page") Integer page , @Param("size") Integer size);

    @Select("SELECT COUNT(*) FROM question WHERE creator=#{userId}")
    Integer selectCountById(@Param("userId") Integer userId);

    @Select("SELECT * FROM question WHERE creator=#{userId} LIMIT #{page} , #{size}")
    List<Question> selectListByCreator(@Param("userId") Integer userId, @Param("page") Integer page , @Param("size") Integer size);

    @Select("SELECT * FROM question WHERE id=#{id}")
    Question selectById(@Param("id") Integer id);

    @Update("UPDATE question SET title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} WHERE id=#{id}")
    void updateQuestion(Question question);
}
