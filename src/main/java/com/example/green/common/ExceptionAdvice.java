package com.example.green.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Green写代码
 * @date 2023-01-12 12:57
 * 异常类的统一处理
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object ExceptionAdvice(Exception e) {
        return AjaxResult.fail(-1, e.getMessage());
    }

}
