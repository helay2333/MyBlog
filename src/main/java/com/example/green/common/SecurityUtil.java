package com.example.green.common;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author Green写代码
 * @date 2023-02-18 01:34
 */
public class SecurityUtil {
    public static String encrypt(String password){
        String salt = UUID.randomUUID().toString().replace("-","");//得到一个固定的32位的盐值
        String finalPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());//生成最终的密码
        return salt + finalPassword;
    }

    public static boolean decrypt(String password, String finalPassword){
        if(!StringUtils.hasLength(password) || !StringUtils.hasLength(finalPassword)){
            return false;
        }
        if(finalPassword.length() != 64){
            return false;
        }
        //盐值
        String salt = finalPassword.substring(0,32);
        //试用盐值+待确认的密码生成最终密码

        String securityPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        securityPassword = salt + securityPassword;
        return finalPassword.equals(securityPassword) ;
    }
}
