package com.example.green.common;

import java.util.HashMap;

/**
 * @author Green写代码
 * @date 2023-01-11 20:01
 */
public class AjaxResult {
    /**
     * 业务执行成功时进行返回的方法
     * @param data
     * @return
     */
    public static HashMap<String, Object> success(Object data){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",200);
        result.put("msg","");
        result.put("data",data);
        return result;
    }

    /**
     * 业务执行成功时进行返回的方法
     * @param data
     * @return
     */
    public static HashMap<String, Object> success(String msg, Object data){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code",200);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }
    /**
     * 业务执行失败的时候返回数据格式
     */
    public static HashMap<String, Object> fail(int code, String msg){
        HashMap<String, Object>result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data","");
        return result;
    }

    public static HashMap<String, Object> fail(int code, String msg, Object data){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
