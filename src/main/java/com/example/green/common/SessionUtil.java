package com.example.green.common;

import com.example.green.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Green写代码
 * @date 2023-01-16 20:16
 * 查询当前登录用户的session信息
 */
public class SessionUtil {
    public static UserInfo getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(ConstantVariable.session_userinfo_key) != null){
            UserInfo userInfo = (UserInfo) session.getAttribute(ConstantVariable.session_userinfo_key);
            return  userInfo;
        }
        return null;
    }
}
