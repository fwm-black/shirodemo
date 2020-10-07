package com.wm.shirodemo.controller.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller异常处理
 */
@ControllerAdvice
public class ControllerExceptionHandle {

    /**
     * 权限不足异常处理,返回指定内容
     * @param request
     * @param response
     * @param e
     * @throws Exception
     */
    @ExceptionHandler(AuthorizationException.class)
    public void AuthorizationExceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
        response.setHeader("Access-Control-Allow-Origin",((HttpServletRequest)request).getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String result = "你TM没有权限";
        response.getWriter().write(result);
    }

}
