package com.token.ankush.service.impl;

import com.token.ankush.UserEntity;
import com.token.ankush.dao.UserDao;
import com.token.ankush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity save(UserEntity user) {
        return userDao.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserEntity findByUserID(Long userID) {
        return userDao.findByUserId(userID);
    }
}
