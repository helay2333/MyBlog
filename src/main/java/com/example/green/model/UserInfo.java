package com.example.green.model;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-01-11 16:12
 */
@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String photo;
    private String createtime;
    private String updatetime;
    private int state;
}
