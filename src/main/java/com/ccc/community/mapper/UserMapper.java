package com.ccc.community.mapper;

import com.ccc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 04 22:54
 */
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(name, account_id, token, gmt_create, gmt_modified ) VALUES (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}) ")
    void insertUser(User user);
}
