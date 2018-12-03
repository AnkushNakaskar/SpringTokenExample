package com.token.ankush.service;

import com.token.ankush.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);

    UserEntity findByEmail(String email);

    UserEntity findByUserID(Long userID);

}
