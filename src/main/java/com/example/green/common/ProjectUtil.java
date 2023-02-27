package com.example.green.common;

import java.util.UUID;

/**
 * @author Green写代码
 * @date 2023-02-25 17:36
 */
public class ProjectUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
