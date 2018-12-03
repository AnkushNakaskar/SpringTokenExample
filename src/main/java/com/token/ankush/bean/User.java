package com.token.ankush.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String password;

    private String email;
}
