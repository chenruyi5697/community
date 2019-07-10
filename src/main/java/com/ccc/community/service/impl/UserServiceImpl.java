package com.ccc.community.service.impl;

import com.ccc.community.mapper.UserMapper;
import com.ccc.community.model.User;
import com.ccc.community.model.UserExample;
import com.ccc.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 05 21:33
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public void updateOrInsert(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(example);
        if (dbUsers.size() == 0){
            //insert
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(dbUsers.get(0).getGmtCreate());
            userMapper.insert(user);
        }else {
            //update
            User dbUser = dbUsers.get(dbUsers.size() - 1);
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.updateByPrimaryKeySelective(dbUser);
        }
    }
}
