package com.token.ankush.controller;

import java.util.Date;

import javax.servlet.ServletException;

import com.token.ankush.UserEntity;
import com.token.ankush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserEntity registerUser(@RequestBody UserEntity user) {
        return userService.save(user);
    }

    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    public UserEntity findByEmail(@PathVariable Long userid) {
        return userService.findByUserID(userid);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserEntity login) throws ServletException {

        String jwtToken = "";

        if (login.getEmail() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String email = login.getEmail();
        String password = login.getPassword();

        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            throw new ServletException("User email not found.");
        }

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }


        jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        return jwtToken;
    }
}