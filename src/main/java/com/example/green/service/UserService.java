package com.example.green.service;

import com.example.green.mapper.UserMapper;
import com.example.green.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Green写代码
 * @date 2023-01-11 16:17
 * 用户表的服务层
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int add(String username, String password, String photo){
        return userMapper.add(username, password, photo);
    }
    public UserInfo login(String username, String password){
        return userMapper.login(username, password);
    }
    public int updatePhoto(Integer id, String photo){
        return userMapper.updatePhoto(id, photo);
    }

    public int updatePassword(Integer id, String password){
        return userMapper.updatePassword(id, password);
    }

    public UserInfo getUserByName(String username){
        return userMapper.getUserByName(username);
    }

    public int getMyArtCount(Integer id){
        return userMapper.getMyCount(id);
    }
}
