package com.token.ankush.controller;


import com.token.ankush.UserEntity;
import com.token.ankush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/secure")
public class SecureController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/users")
    public String loginSuccess() {
        return "Login Successful!";
    }

    @RequestMapping(value = "/user/email", method = RequestMethod.POST)
    public UserEntity findByEmail(@RequestBody String email) {
        return userService.findByEmail(email);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public UserEntity updateUser(@RequestBody UserEntity user) {
        return userService.save(user);
    }
}
