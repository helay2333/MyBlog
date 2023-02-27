package com.example.green.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Green写代码
 * @date 2023-01-13 00:53
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {
    //不拦截的utl
    List<String> excludes = new ArrayList<String>(){{
        add("/**/*.html");
        add("/**/*.js");//如果只写/*.js 是不会被探测到的
        add("/**/*.jpg");
        add("/**/*.css");
        add("/**/*.png");
        add("/art/detail");
        add("/user/login");

        add("/user/reg");
        add("/img/**"); // 表示放行static/img 下面的所有文件
        add("/js/**"); // 表示放行static/js 下面的所有文件
        add("/css/**"); // 表示放行static/css 下面的所有文件
        add("/editor.md/**"); // 表示放行static/editor 下面的所有文件
    }};

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistry = registry.addInterceptor(loginInterceptor);
        interceptorRegistry.addPathPatterns("/**");
        interceptorRegistry.excludePathPatterns(excludes);

    }
}
