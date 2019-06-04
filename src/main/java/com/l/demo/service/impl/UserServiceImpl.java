package com.l.demo.service.impl;

import com.l.demo.mapper.UserMapper;
import com.l.demo.mapper.UserMapper;
import com.l.demo.model.User;
import com.l.demo.service.UserService;
import com.l.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User model) {
        userMapper.insert(model);
    }

    @Override
    public void update(User model) {
        userMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public List<User> queryData(User user) {
        return userMapper.selectData(user);
    }

    @Override
    public User queryDataById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
