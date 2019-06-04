package com.l.demo.service;

import com.l.demo.model.User;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
public interface UserService {

    List<User> queryData(User user);


    User queryDataById(Integer integer);

    void insert(User model);

    void update(User model);
}
