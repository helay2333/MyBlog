package com.example.green.mapper;

import com.example.green.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface UserMapper {
    public int add(@Param("username") String username, @Param("password") String password, @Param("photo")String photo);

    public UserInfo login(@Param("username") String username,
                          @Param("password") String password);

    public int updatePhoto(@Param("id") Integer id, @Param("photo") String photo);

    public int updatePassword(@Param("id")Integer id, @Param("password")String password);

    public UserInfo getUserByName(@Param("username")String username);

    public int getMyCount(@Param("id")Integer id);
}
