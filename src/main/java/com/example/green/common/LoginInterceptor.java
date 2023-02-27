package com.example.green.common;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Green写代码
 * @date 2023-01-13 00:14
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("正在走拦截器" + request.getRequestURI());
        //获取得到用户信息
        //如果拿到userinfo对象,说明用户登录了, 否则没登录
        HttpSession session = request.getSession(false);//false表示不创建
        System.out.println(session);
        if(session != null && session.getAttribute(ConstantVariable.session_userinfo_key)  != null){
            return true;
        }
        response.setStatus(401);
        return false;
    }
}
