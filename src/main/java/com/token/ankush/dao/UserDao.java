package com.token.ankush.dao;

import com.token.ankush.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long> {
    UserEntity save(UserEntity user);

    UserEntity findByEmail(String email);

    UserEntity findByUserId(Long userID);
}